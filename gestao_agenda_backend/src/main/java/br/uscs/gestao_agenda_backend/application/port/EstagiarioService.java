package br.uscs.gestao_agenda_backend.application.port;

import br.uscs.gestao_agenda_backend.application.request.CadastroEstagiarioRequest;
import br.uscs.gestao_agenda_backend.application.request.UpdateEstagiarioRequest;
import br.uscs.gestao_agenda_backend.application.dto.EstagiarioResponse;

import java.time.LocalDate;
import java.util.List;

public interface EstagiarioService {
    EstagiarioResponse cadastrarEstagiario(CadastroEstagiarioRequest request);

    List<EstagiarioResponse> getAllAvailableInDataRange(LocalDate startDate, LocalDate endDate);

    List<EstagiarioResponse> findAll();

    EstagiarioResponse updateEstagiario(UpdateEstagiarioRequest request);
}
