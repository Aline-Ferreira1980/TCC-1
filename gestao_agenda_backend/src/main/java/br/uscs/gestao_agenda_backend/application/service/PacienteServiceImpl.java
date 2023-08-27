package br.uscs.gestao_agenda_backend.application.service;

import br.uscs.gestao_agenda_backend.application.common.PacienteMapper;
import br.uscs.gestao_agenda_backend.application.dto.out.PacienteResponse;
import br.uscs.gestao_agenda_backend.application.port.PacienteService;
import br.uscs.gestao_agenda_backend.domain.model.Paciente;
import br.uscs.gestao_agenda_backend.domain.port.PacienteRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PacienteServiceImpl implements PacienteService {

    @Autowired
    private final PacienteRepository pacienteRepository;


    @Override
    public void cadastraPaciente(Paciente paciente) {
        if (pacienteRepository.findByEmail(paciente.getEmail()) != null) {
            throw new RuntimeException("O email já está em uso.");
        }

        String senhaCriptografada = new BCryptPasswordEncoder().encode(paciente.getSenha());
        paciente.setSenha(senhaCriptografada);

        pacienteRepository.save(paciente);
    }

    @Override
    public List<PacienteResponse> findAll() {
        return pacienteRepository.findAll().stream().map(PacienteMapper::toResponse).toList();
    }
}
