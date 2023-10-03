package br.uscs.gestao_agenda_backend.infrastructure.web;

import br.uscs.gestao_agenda_backend.application.dto.AgendamentoResponse;
import br.uscs.gestao_agenda_backend.application.port.AgendamentoService;
import br.uscs.gestao_agenda_backend.application.request.AgendamentoRequest;
import br.uscs.gestao_agenda_backend.infrastructure.security.permissions.CheckSecurity;
import br.uscs.gestao_agenda_backend.infrastructure.web.openapi.AgendamentoControllerOpenApi;
import lombok.AllArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("agendamento")
public class AgendamentoController implements AgendamentoControllerOpenApi {

    private final AgendamentoService agendamentoService;

    @Override
    @PostMapping(value = "/agendar", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> cadastrarPaciente(AgendamentoRequest request,
                                               UriComponentsBuilder uriBuilder) {

        try {
            Optional<AgendamentoResponse> response = agendamentoService.cadastraAgendametno(request);
            if (response.isPresent()) {

                URI uri = uriBuilder.path("/paciente/{id}").buildAndExpand(response.get().getId()).toUri();
                return ResponseEntity.created(uri).body(response.get());
            }
            return ResponseEntity.badRequest().build();
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @Override
    @CheckSecurity.User.CanViewListAgendamento
    @GetMapping(value = "/user/{id}")
    public ResponseEntity<List<AgendamentoResponse>> findAgendamentoByUserId(Long id) {
        List<AgendamentoResponse> response = agendamentoService.findAgendamentosByUserId(id);
        return (response != null) ? ResponseEntity.ok(response) : ResponseEntity.notFound().build();

    }

    @Override
    @GetMapping(value = "/sala/{id}")
    public ResponseEntity<List<AgendamentoResponse>> findAgendamentoBySalaId(Long id) {
        List<AgendamentoResponse> response = agendamentoService.findAgendamentosBySalaId(id);
        return (response != null) ? ResponseEntity.ok(response) : ResponseEntity.notFound().build();
    }

    @Override
    @GetMapping("/findByDate")
    public ResponseEntity<List<AgendamentoResponse>> findAgendamentoByDateRange(LocalDateTime inicio,
                                                                                LocalDateTime fim) {
        List<AgendamentoResponse> response = agendamentoService.findAgendamentosByDateRange(inicio, fim);
        return (response != null) ? ResponseEntity.ok(response) : ResponseEntity.notFound().build();
    }

    @Override
    @GetMapping("/user/{userId}/findByDate")
    public ResponseEntity<List<AgendamentoResponse>> findAgendamentoByUserIdAndDateRange(Long userId,
                                                                                         LocalDateTime inicio,
                                                                                         LocalDateTime fim) {
        List<AgendamentoResponse> response = agendamentoService
                .findAgendamentoByUserIdAndDateRange(userId, inicio, fim);

        return (response != null) ? ResponseEntity.ok(response) : ResponseEntity.notFound().build();
    }

    @Override
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AgendamentoResponse> atualizaAgendamento(Long id, AgendamentoRequest request) {

        Optional<AgendamentoResponse> response = agendamentoService.atualizaAgendamento(id, request);
        return response.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Override
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteAgendamento(Long id) {
        try {
            agendamentoService.deleteAgendamento(id);
            return ResponseEntity.ok("Agendamento deletado com sucesso");
        } catch (EmptyResultDataAccessException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
