package br.uscs.gestao_agenda_backend.application.port;

import br.uscs.gestao_agenda_backend.application.dto.DocenteResponse;
import br.uscs.gestao_agenda_backend.application.dto.SalaResponse;
import br.uscs.gestao_agenda_backend.application.request.CadastroDocenteRequest;
import br.uscs.gestao_agenda_backend.domain.model.Docente;

import java.util.List;
import java.util.Optional;

public interface DocenteService {
    DocenteResponse cadastraDocente(Docente request);

    List<DocenteResponse> findAll();

    Optional<DocenteResponse> findById(Long id);

    Optional<DocenteResponse> atualizaDocente(Long id, Docente docente);

    void deletaDocente(Long id);
}
