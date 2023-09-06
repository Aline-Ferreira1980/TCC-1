package br.uscs.gestao_agenda_backend.application.service;

import br.uscs.gestao_agenda_backend.application.common.PacienteMapper;
import br.uscs.gestao_agenda_backend.application.dto.PacienteResponse;
import br.uscs.gestao_agenda_backend.application.port.PacienteService;
import br.uscs.gestao_agenda_backend.application.request.AtualizaPacienteRequest;
import br.uscs.gestao_agenda_backend.domain.model.Paciente;
import br.uscs.gestao_agenda_backend.domain.port.PacienteRepository;
import com.fasterxml.jackson.databind.util.BeanUtil;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PacienteServiceImpl implements PacienteService {

    private final PacienteRepository pacienteRepository;
    private final PacienteMapper pacienteMapper;


    @Override
    public PacienteResponse cadastraPaciente(Paciente paciente) {
        if (pacienteRepository.findByEmail(paciente.getEmail()) != null) {
            // TODO: Criar uma exeção customizad
            throw new RuntimeException("O email já está em uso.");
        }

        String senhaCriptografada = new BCryptPasswordEncoder().encode(paciente.getSenha());
        paciente.setSenha(senhaCriptografada);

        return pacienteMapper.toResponse(pacienteRepository.save(paciente));
    }

    @Override
    public List<PacienteResponse> findAll() {
        return pacienteRepository.findAll().stream().map(pacienteMapper::toResponse).toList();
    }

    @Override
    public Optional<PacienteResponse> findById(Long id) {
        Optional<Paciente> paciente = pacienteRepository.findById(id);
        return paciente.map(pacienteMapper::toResponse);

    }

    @Override
    public Optional<PacienteResponse> atualizaPaciente(Long id, Paciente request) {
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
        // TODO: Mandar erro customizado
        pacienteRepository.deleteById(id);
        pacienteRepository.flush();
    }
}
