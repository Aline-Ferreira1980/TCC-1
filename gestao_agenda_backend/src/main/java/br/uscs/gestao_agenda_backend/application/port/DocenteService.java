package br.uscs.gestao_agenda_backend.application.port;

import br.uscs.gestao_agenda_backend.application.dto.DocenteResponse;
import br.uscs.gestao_agenda_backend.application.request.CadastroDocenteRequest;
import br.uscs.gestao_agenda_backend.domain.model.Docente;

public interface DocenteService {
    DocenteResponse cadastraDocente(Docente request);
}
