package br.uscs.gestao_agenda_backend.application.port;

import br.uscs.gestao_agenda_backend.application.dto.in.CadastroPsicologoRequest;
import br.uscs.gestao_agenda_backend.application.dto.in.UpdatePsicologoRequest;
import br.uscs.gestao_agenda_backend.application.dto.out.PsicologoResponse;

import java.time.LocalDate;
import java.util.List;

public interface PsicologoService {
    void cadastrarPsicologo(CadastroPsicologoRequest request);

    List<PsicologoResponse> getAllAvailableInDataRange(LocalDate startDate, LocalDate endDate);

    List<PsicologoResponse> findAll();

    PsicologoResponse uodatePsicologo(UpdatePsicologoRequest request);
}
