package br.uscs.gestao_agenda_backend.application.port;

import br.uscs.gestao_agenda_backend.application.dto.AgendamentoResponse;
import br.uscs.gestao_agenda_backend.application.request.AgendamentoRequest;

import java.util.Optional;

public interface AgendamentoService {

    public Optional<AgendamentoResponse> cadastraAgendametno(AgendamentoRequest request);

    Optional<AgendamentoResponse> findAgendamentoByUserId(Long id);
}
