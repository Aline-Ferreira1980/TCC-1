package br.uscs.gestao_agenda_backend.application.port;

import br.uscs.gestao_agenda_backend.application.dto.out.PacienteResponse;
import br.uscs.gestao_agenda_backend.domain.model.Paciente;

import java.util.List;

public interface PacienteService {
    void cadastraPaciente(Paciente request);

    List<PacienteResponse> findAll();
}
