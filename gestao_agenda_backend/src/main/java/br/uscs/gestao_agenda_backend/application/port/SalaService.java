package br.uscs.gestao_agenda_backend.application.port;

import br.uscs.gestao_agenda_backend.application.request.SalaRequest;
import br.uscs.gestao_agenda_backend.application.dto.SalaResponse;
import br.uscs.gestao_agenda_backend.domain.model.Paciente;
import br.uscs.gestao_agenda_backend.domain.model.Sala;

import java.util.List;
import java.util.Optional;

public interface SalaService {

    SalaResponse cadastraSala(Sala request);

    List<SalaResponse> findAll();

    Optional<SalaResponse> findById(Long id);

    Optional<SalaResponse> atualizaSala(Long id, Sala request);

    void deletaSala(Long id);
}
