package br.uscs.gestao_agenda_backend.application.port;

import br.uscs.gestao_agenda_backend.application.dto.PacienteResponse;
import br.uscs.gestao_agenda_backend.domain.model.Paciente;

import java.util.List;
import java.util.Optional;

public interface PacienteService {
    PacienteResponse cadastraPaciente(Paciente request);

    List<PacienteResponse> findAll();

    Optional<PacienteResponse> findById(Long id);

    Optional<PacienteResponse> atualizaPaciente(Long id, Paciente request);

    void deletaPaciente(Long id);
}
