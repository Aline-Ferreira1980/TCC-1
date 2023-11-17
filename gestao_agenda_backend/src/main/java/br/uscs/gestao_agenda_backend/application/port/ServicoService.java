package br.uscs.gestao_agenda_backend.application.port;

import br.uscs.gestao_agenda_backend.application.dto.ServicoResponse;
import br.uscs.gestao_agenda_backend.application.request.ServicoRequest;

import java.util.List;
import java.util.Optional;

public interface ServicoService {
    Optional<ServicoResponse> cadastrarServico(ServicoRequest request);

    Optional<ServicoResponse> findById(Long id);

    List<ServicoResponse> findAll();

    Optional<ServicoResponse> udpateServico(Long id, ServicoRequest request);

    void deletaServico(Long id);

    Optional<ServicoResponse> addEstagiarioToServico(Long servicoId, Long estagiarioId);


    Optional<ServicoResponse> removeEstagiarioFromServico(Long idServico, Long idEstagiario);

    Optional<ServicoResponse> addDocenteToServico(Long idServico, Long idDocente);

    Optional<ServicoResponse> removeDocenteFromServico(Long idServico, Long idDocente);

    Optional<ServicoResponse> addEstagiariosToServico(Long idServico, List<Long> idEstagiarios);

    Optional<ServicoResponse> addDocentesToServico(Long idServico, List<Long> idDocentes);
}
