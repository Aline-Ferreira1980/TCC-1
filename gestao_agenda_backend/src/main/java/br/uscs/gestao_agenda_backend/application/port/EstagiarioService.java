package br.uscs.gestao_agenda_backend.application.port;

import br.uscs.gestao_agenda_backend.application.dto.EstagiarioResponse;
import br.uscs.gestao_agenda_backend.application.request.AtualizaEstagiarioRequest;
import br.uscs.gestao_agenda_backend.application.request.CadastroEstagiarioRequest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface EstagiarioService {
    EstagiarioResponse cadastrarEstagiario(CadastroEstagiarioRequest request);

    List<EstagiarioResponse> getAllAvailableInDataRange(LocalDate startDate, LocalDate endDate);

    List<EstagiarioResponse> findAll();

    Optional<EstagiarioResponse> updateEstagiario(Long id, AtualizaEstagiarioRequest request);

    Optional<EstagiarioResponse> findById(Long id);

    void deletaEstagiario(Long id);

    List<EstagiarioResponse> findByServico(String servicoAcronimo);

    Optional<EstagiarioResponse> addDocente(Long idEstagiario, Long idDocente);
}
