package br.uscs.gestao_agenda_backend.application.service;

import br.uscs.gestao_agenda_backend.application.common.AgendamentoMapper;
import br.uscs.gestao_agenda_backend.application.dto.AgendamentoResponse;
import br.uscs.gestao_agenda_backend.application.port.AgendamentoService;
import br.uscs.gestao_agenda_backend.application.request.AgendamentoRequest;
import br.uscs.gestao_agenda_backend.domain.model.*;
import br.uscs.gestao_agenda_backend.domain.port.AgendamentoRespository;
import br.uscs.gestao_agenda_backend.domain.port.EstagiarioRepository;
import br.uscs.gestao_agenda_backend.domain.port.PacienteRepository;
import br.uscs.gestao_agenda_backend.domain.port.SalaRepository;
import br.uscs.gestao_agenda_backend.infrastructure.security.permissions.AppSecurity;
import lombok.AllArgsConstructor;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.common.exceptions.UnauthorizedUserException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AgendamentoServiceImpl implements AgendamentoService {
    private final AgendamentoRespository agendamentoRepository;
    private final EstagiarioRepository estagiarioRepository;
    private final PacienteRepository pacienteRepository;
    private final SalaRepository salaRepository;

    private final AgendamentoMapper agendamentoMapper;
    private final AppSecurity appSecurity;

    @Transactional
    @Override
    public Optional<AgendamentoResponse> cadastraAgendametno(AgendamentoRequest request) {
        // Verifica se paciente esta criando agendamento para ele mesmo
        this.validatePacienteAuthority(request.getPacienteEmail(),
                "Paciente tem acesso somente para criação de agendamentos para si próprio");

        // Verifica disponibilidade
        // Valida se existe agendamento marcado em uma data e hora especifica,
        // independentemente do estagiario, paciente ou sala
        Optional<Agendamento> dinsponivel = agendamentoRepository.findByDataAgendamentoIndependenteIntegrantes(
                request.getEstagiarioEmail(),
                request.getPacienteEmail(),
                request.getSalaId(),
                request.getInicioAgendamento()
        );
        if(dinsponivel.isPresent()){
            // TODO criar erro para um dos integegranstes ja tem uma agendamento no horario
            throw new IllegalArgumentException("Psicólogo, paciente ou sala ja possuem angendamento neste horario.");
//            return Optional.empty();
        }


        // Verifica se estagiario existe
        Optional<Estagiario> estag = estagiarioRepository.findByEmail(request.getEstagiarioEmail());

        if (estag.isEmpty()) {
            // TODO criar erro para usuario nao encontrado
            throw new EntityNotFoundException("Estagiario informado nao existe.");
//            return Optional.empty();
        }

        //Verifica se estagiario trabalha na data e hora marcada.
        LocalTime inicioAgendamento = request.getInicioAgendamento().toLocalTime();
        LocalTime fimAgendamento = request.getFimAgendamento().toLocalTime();
        DayOfWeek diaSemanaAgendamento = request.getInicioAgendamento().getDayOfWeek();
        // Valdia se estagiario trabalha no dia da semana do agendamento

        if (!(estag.get().trabalhaNoDia(diaSemanaAgendamento))){
            throw new IllegalArgumentException("Estagiario informado nao trabalha no dia da semana informado.");
        }else if(!(estag.get().trabalhaNoRangeDeHorario(inicioAgendamento, fimAgendamento))){
            // TODO criar erro para estagiario nao trabalha no dia da semana ou horario informado
            throw new IllegalArgumentException("Estagiario informado nao trabalha no horario informado.");
//            return Optional.empty();
        }

        // busca paciente por email
        Optional<Paciente> paciente = pacienteRepository.findByEmail(request.getPacienteEmail());
        if (paciente.isEmpty()){
            // TODO criar erro para paciente nao encontrado
            throw new EntityNotFoundException("Paciente informado nao existe.");
//            return Optional.empty();
        }

        Optional<Sala> sala = salaRepository.findById(request.getSalaId());
        // busca sala por id
        if (sala.isEmpty()) {
            // TODO criar erro para sala nao encontrada
            throw new EntityNotFoundException("Sala informada nao existe.");
//            return Optional.empty();
        }

        // cria agendamento
        Agendamento agendamento = Agendamento.builder()
                .estagiario(estag.get())
                .paciente(paciente.get())
                .sala(sala.get())
                .inicioAgendamento(request.getInicioAgendamento())
                .fimAgendamento(request.getFimAgendamento()).build();

        // salva agendamento
        AgendamentoResponse response = agendamentoMapper.toResponse(agendamentoRepository.save(agendamento));

        return Optional.ofNullable(response);
    }

    @Override
    public List<AgendamentoResponse> findAgendamentosByUserId(Long id) {
        this.validatePacienteAuthority(id);

        Optional<List<Agendamento>> agendamentos = agendamentoRepository.findAgendamentosByUserId(id);

        return agendamentos.map(agendamentoList -> agendamentoList.stream()
                .map(agendamentoMapper::toResponse)
                .toList()).orElse(null);
    }

    @Override
    public List<AgendamentoResponse> findAgendamentosBySalaId(Long id) {
        Optional<List<Agendamento>> agendamentos = agendamentoRepository.findBySalaId(id);
        return agendamentos.map(agendamentoList -> agendamentoList.stream()
                .map(agendamentoMapper::toResponse)
                .toList()).orElse(null);
    }

    @Override
    public List<AgendamentoResponse> findAgendamentosByDateRange(LocalDateTime inicio, LocalDateTime fim) {
        Optional<List<Agendamento>> agendamentos = agendamentoRepository.findAgendamentosByTimeRange(inicio, fim);
        return agendamentos.map(agendamentoList -> agendamentoList.stream()
                .map(agendamentoMapper::toResponse)
                .toList()).orElse(null);
    }

    @Override
    public List<AgendamentoResponse> findAgendamentoByUserIdAndDateRange(Long userId,
                                                                         LocalDateTime inicio,
                                                                         LocalDateTime fim) {

        this.validatePacienteAuthority(userId);

        Optional<List<Agendamento>> agendamentos = agendamentoRepository
                .findAgendamentoByEstagiarioIdAndDateRange(userId, inicio, fim);

        return agendamentos.map(agendamentoList -> agendamentoList.stream()
                .map(agendamentoMapper::toResponse)
                .toList()).orElse(null);
    }

    @Override
    public Optional<AgendamentoResponse> atualizaAgendamento(Long id, AgendamentoRequest request) {

        Agendamento agendamentoAtual =  agendamentoRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Nao foi possível encontrar o agendamento a ser deletado")
        );

        this.validatePacienteAuthority(agendamentoAtual.getPaciente().getId());

//        Agendamento agendamentoAtual = rs.get();

        // Verifica disponibilidade
        // Valida se existe agendamento marcado em uma data e hora especifica,
        // independentemente do estagiario, paciente ou sala
        Optional<Agendamento> dinsponivel = agendamentoRepository.findByDataAgendamentoIndependenteIntegrantes(
                request.getEstagiarioEmail(),
                request.getPacienteEmail(),
                request.getSalaId(),
                request.getInicioAgendamento()
        );

        if(dinsponivel.isPresent()){
            // TODO criar erro para um dos integegranstes ja tem uma agendamento no horario
            throw new IllegalArgumentException("Psicólogo, paciente ou sala ja possuem angendamento neste horario.");
//            return Optional.empty();
        }


        // Verifica se novo estagiario existe
        Optional<Estagiario> estag = estagiarioRepository.findByEmail(request.getEstagiarioEmail());

        if (estag.isEmpty()) {
            // TODO criar erro para usuario nao encontrado
            throw new IllegalArgumentException("Estagiario informado nao existe.");
        }

        //Verifica se estagiário trabalha na data e hora marcada.
        LocalTime inicioAgendamento = request.getInicioAgendamento().toLocalTime();
        LocalTime fimAgendamento = request.getFimAgendamento().toLocalTime();
        DayOfWeek diaSemanaAgendamento = request.getInicioAgendamento().getDayOfWeek();
        // Valia se estagiário trabalha no dia da semana do agendamento

        if (!(estag.get().trabalhaNoDia(diaSemanaAgendamento))){
            throw new IllegalArgumentException("Estagiario informado nao trabalha no dia da semana informado.");
        }else if(!(estag.get().trabalhaNoRangeDeHorario(inicioAgendamento, fimAgendamento))){
            // TODO criar erro para estagiario nao trabalha no dia da semana ou horario informado
            throw new IllegalArgumentException("Estagiario informado nao trabalha no horario informado.");
        }

        // busca paciente por email
        Optional<Paciente> paciente = pacienteRepository.findByEmail(request.getPacienteEmail());
        if (paciente.isEmpty()){
            // TODO criar erro para paciente nao encontrado
            throw new IllegalArgumentException("Paciente informado nao existe.");
        }

        Optional<Sala> sala = salaRepository.findById(request.getSalaId());
        // busca sala por id
        if (sala.isEmpty()) {
            // TODO criar erro para sala nao encontrada
            throw new IllegalArgumentException("Sala informada nao existe.");
        }

        // atualiza agendamento
        agendamentoAtual.setEstagiario(estag.get());
        agendamentoAtual.setPaciente(paciente.get());
        agendamentoAtual.setSala(sala.get());
        agendamentoAtual.setInicioAgendamento(request.getInicioAgendamento());
        agendamentoAtual.setFimAgendamento(request.getFimAgendamento());

        // salva agendamento
        AgendamentoResponse response = agendamentoMapper.toResponse(agendamentoRepository.save(agendamentoAtual));

        return Optional.ofNullable(response);
    }

    @Override
    public void deleteAgendamento(Long id) {

        Agendamento agendamento =  agendamentoRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Nao foi possível encontrar o agendamento a ser deletado")
        );
        this.validatePacienteAuthority(agendamento.getPaciente().getId());

        agendamentoRepository.deleteById(id);
        agendamentoRepository.flush();
    }


    private void validatePacienteAuthority(Long userId, String...errorMessage){
        String message = errorMessage.length > 0 ?
                errorMessage[0] : "Paciente somente tem permissão eu seus próprios agendamentos";

        Long tokneUserId= appSecurity.getUserId();
        List<String> roles = appSecurity.getRoles();

        if(roles.contains("paciente") && (!userId.equals(tokneUserId))){
            throw new UnauthorizedUserException(message);
        }
    }
    private void validatePacienteAuthority(String userEmail, String...errorMessage){
        String message = errorMessage.length > 0 ?
                errorMessage[0] : "Paciente somente tem permissão eu seus próprios agendamentos";

        String tokenUserEmail= appSecurity.getEmail();
        List<String> roles = appSecurity.getRoles();

        if(roles.contains("paciente") && (!userEmail.equals(tokenUserEmail))){
            throw new UnauthorizedUserException("Paciente somente tem permissão eu seus próprios agendamentos");
        }
    }


}
