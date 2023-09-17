package br.uscs.gestao_agenda_backend.application.port;

import br.uscs.gestao_agenda_backend.application.dto.AgendamentoResponse;
import br.uscs.gestao_agenda_backend.application.request.AgendamentoRequest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface AgendamentoService {

    Optional<AgendamentoResponse> cadastraAgendametno(AgendamentoRequest request);

    List<AgendamentoResponse> findAgendamentosByUserId(Long id);

    List<AgendamentoResponse> findAgendamentosBySalaId(Long id);

    List<AgendamentoResponse> findAgendamentosByDateRange(LocalDateTime inicio, LocalDateTime fim);

    List<AgendamentoResponse> findAgendamentoByUserIdAndDateRange(Long userId, LocalDateTime inicio, LocalDateTime fim);

    Optional<AgendamentoResponse> atualizaAgendamento(Long id, AgendamentoRequest request);

    void deleteAgendamento(Long id);
}
