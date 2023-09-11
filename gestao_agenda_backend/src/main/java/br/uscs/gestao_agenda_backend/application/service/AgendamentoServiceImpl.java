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
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AgendamentoServiceImpl implements AgendamentoService {
    private final AgendamentoRespository agendamentoRepository;
    private final EstagiarioRepository estagiarioRepository;
    private final PacienteRepository pacienteRepository;
    private final SalaRepository salaRepository;

    private final AgendamentoMapper agendamentoMapper;

    @Transactional
    @Override
    public Optional<AgendamentoResponse> cadastraAgendametno(AgendamentoRequest request) {
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
            throw new IllegalArgumentException("Estagiario informado nao existe.");
//            return Optional.empty();
        }

        //Verifica se estagiario trabalha na data e hora marcada.
        LocalTime inicioAgendamento = request.getInicioAgendamento().toLocalTime();
        LocalTime fimAgendamento = request.getFimAgendamento().toLocalTime();
        DayOfWeek diaSemanaAgendamento = request.getInicioAgendamento().getDayOfWeek();
        // Valdia se estagiario trabalha no dia da semana do agendamento

        if (!(estag.get().trabalhaNoDia(diaSemanaAgendamento))){
            throw new IllegalArgumentException("Estagiario informado nao trabalha no dia da semana informado.");
        }else if(estag.get().trabalhaNoRangeDeHorario(inicioAgendamento, fimAgendamento)){
            // TODO criar erro para estagiario nao trabalha no dia da semana ou horario informado
            throw new IllegalArgumentException("Estagiario informado nao trabalha no horario informado.");
//            return Optional.empty();
        }

        // busca paciente por email
        Optional<Paciente> paciente = pacienteRepository.findByEmail(request.getPacienteEmail());
        if (paciente.isEmpty()){
            // TODO criar erro para paciente nao encontrado
            throw new IllegalArgumentException("Paciente informado nao existe.");
//            return Optional.empty();
        }

        Optional<Sala> sala = salaRepository.findById(request.getSalaId());
        // busca sala por id
        if (sala.isEmpty()) {
            // TODO criar erro para sala nao encontrada
            throw new IllegalArgumentException("Sala informada nao existe.");
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
    public Optional<AgendamentoResponse> findAgendamentoByUserId(Long id) {
        Optional<Agendamento> agendamento = agendamentoRepository.findAgendamentoByUserId(id);
        return agendamento.map(agendamentoMapper::toResponse);

    }
}
