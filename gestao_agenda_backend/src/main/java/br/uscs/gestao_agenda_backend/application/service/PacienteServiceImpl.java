package br.uscs.gestao_agenda_backend.application.service;

import br.uscs.gestao_agenda_backend.application.common.PacienteMapper;
import br.uscs.gestao_agenda_backend.application.dto.PacienteResponse;
import br.uscs.gestao_agenda_backend.application.port.ConfirmacaoService;
import br.uscs.gestao_agenda_backend.application.port.PacienteService;
import br.uscs.gestao_agenda_backend.domain.model.Paciente;
import br.uscs.gestao_agenda_backend.domain.model.enums.UserRole;
import br.uscs.gestao_agenda_backend.domain.port.PacienteRepository;
import br.uscs.gestao_agenda_backend.infrastructure.security.permissions.AppSecurity;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.common.exceptions.UnauthorizedUserException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PacienteServiceImpl implements PacienteService {

    private final PacienteRepository pacienteRepository;
    private final PacienteMapper pacienteMapper;

    private final ConfirmacaoService confirmacaoService;
    private final AppSecurity appSecurity;


    @Override
    public PacienteResponse cadastraPaciente(Paciente paciente) {
        if (pacienteRepository.findByEmail(paciente.getEmail()).isPresent()) {

            throw new EntityExistsException("Email para paciente ja esta em uso");
        }

        String senhaCriptografada = new BCryptPasswordEncoder().encode(paciente.getSenha());
        paciente.setSenha(senhaCriptografada);

        paciente.setRole(UserRole.PACIENTE);

        paciente.setConfirmed(false);
        String token = UUID.randomUUID().toString();
        paciente.setToken(token);
        confirmacaoService.enviarEmailConfirmacao(paciente.getEmail(), token);

        return pacienteMapper.toResponse(pacienteRepository.save(paciente));
    }

    @Override
    public List<PacienteResponse> findAll() {
        return pacienteRepository.findAll().stream().map(pacienteMapper::toResponse).toList();
    }

    @Override
    public Optional<PacienteResponse> findById(Long id) {
        if(appSecurity.validateUserAuthority("paciente",id)){
            throw new UnauthorizedUserException("Usuário nao possui acesso para visualizar o perfil informado");
        }
        Optional<Paciente> paciente = pacienteRepository.findById(id);
        return paciente.map(pacienteMapper::toResponse);

    }

    @Override
    public Optional<PacienteResponse> atualizaPaciente(Long id, Paciente request) {
        if(appSecurity.validateUserAuthority("paciente",id)){
            throw new UnauthorizedUserException("Usuário nao possui acesso para atualizar o perfil informado");
        }

        Optional<Paciente> rs = pacienteRepository.findById(id);

        if (rs.isPresent()){
            Paciente paciente = rs.get();
            BeanUtils.copyProperties(request, paciente, "id", "agendamentos", "email", "estagiario");
            return Optional.ofNullable(pacienteMapper.toResponse(pacienteRepository.save(paciente)));
        }
        return Optional.empty();
    }

    @Override
    public void deletaPaciente(Long id) {
        Paciente paciente = pacienteRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Nao foi possivel encontrar o Paciente a ser deletado")
        );
        if(appSecurity.validateUserAuthority("paciente",id)){
            throw new UnauthorizedUserException("Usuário nao possui acesso para atualizar o perfil informado");
        }

        pacienteRepository.delete(paciente);
        pacienteRepository.flush();
    }

    @Override
    public List<PacienteResponse> findByEstagiarioId(Long estagiario_id) {

        Optional<List<Paciente>> pacientes = pacienteRepository.findByEstagiarioId(estagiario_id);
        return pacientes.map(pacienteList -> pacienteList.stream().
                map(pacienteMapper::toResponse).toList()).orElse(null);
    }

    @Override
    public List<PacienteResponse> findByEstagiarioEmpty() {

        return pacienteRepository.findByEstagiarioIsNull()
                .stream()
                .map(pacienteMapper::toResponse)
                .collect(Collectors.toList());

    }
}
