package br.uscs.gestao_agenda_backend.infrastructure.web;

import br.uscs.gestao_agenda_backend.application.dto.AgendamentoResponse;
import br.uscs.gestao_agenda_backend.application.port.AgendamentoService;
import br.uscs.gestao_agenda_backend.application.request.AgendamentoRequest;
import br.uscs.gestao_agenda_backend.infrastructure.security.permissions.AppSecurity;
import br.uscs.gestao_agenda_backend.infrastructure.security.permissions.CheckSecurity;
import br.uscs.gestao_agenda_backend.infrastructure.web.openapi.AgendamentoControllerOpenApi;
import lombok.AllArgsConstructor;
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
@CrossOrigin
@RequestMapping("agendamento")
public class AgendamentoController implements AgendamentoControllerOpenApi {

    private final AgendamentoService agendamentoService;

    @Override
    @CheckSecurity.Agendamento.CanManageAgendamento
    @PostMapping(value = "/agendar", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> cadastrarAgendamento(AgendamentoRequest request,
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
    @CheckSecurity.Agendamento.CanManageAgendamento
    @GetMapping(value = "/user/{id}")
    public ResponseEntity<List<AgendamentoResponse>> findAgendamentoByUserId(Long id) {
        List<AgendamentoResponse> response = agendamentoService.findAgendamentosByUserId(id);
        return (response != null) ? ResponseEntity.ok(response) : ResponseEntity.notFound().build();

    }

    @Override
    @CheckSecurity.Agendamento.CanViewAgendamentoAdmin
    @GetMapping(value = "/sala/{id}")
    public ResponseEntity<List<AgendamentoResponse>> findAgendamentoBySalaId(Long id) {
        List<AgendamentoResponse> response = agendamentoService.findAgendamentosBySalaId(id);
        return (response != null) ? ResponseEntity.ok(response) : ResponseEntity.notFound().build();
    }

    @Override
    @CheckSecurity.Agendamento.CanViewAgendamentoAdmin
    @GetMapping("/findByDate")
    public ResponseEntity<List<AgendamentoResponse>> findAgendamentoByDateRange(LocalDateTime inicio,
                                                                                LocalDateTime fim) {
        List<AgendamentoResponse> response = agendamentoService.findAgendamentosByDateRange(inicio, fim);
        return (response != null) ? ResponseEntity.ok(response) : ResponseEntity.notFound().build();
    }

    @Override
    @CheckSecurity.Agendamento.CanManageAgendamento
    @GetMapping("/user/{userId}/findByDate")
    public ResponseEntity<List<AgendamentoResponse>> findAgendamentoByUserIdAndDateRange(Long userId,
                                                                                         LocalDateTime inicio,
                                                                                         LocalDateTime fim) {
        List<AgendamentoResponse> response = agendamentoService
                .findAgendamentoByUserIdAndDateRange(userId, inicio, fim);

        return (response != null) ? ResponseEntity.ok(response) : ResponseEntity.notFound().build();
    }

    @Override
    @CheckSecurity.Agendamento.CanManageAgendamento
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AgendamentoResponse> atualizaAgendamento(Long id, AgendamentoRequest request) {

        Optional<AgendamentoResponse> response = agendamentoService.atualizaAgendamento(id, request);
        return response.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Override
    @CheckSecurity.Agendamento.CanManageAgendamento
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteAgendamento(Long id) {

        agendamentoService.deleteAgendamento(id);
        return ResponseEntity.ok("Agendamento deletado com sucesso");

    }

    @Override
    @CheckSecurity.Agendamento.CanManageAgendamento
    @GetMapping(value = "/{id}")
    public ResponseEntity<AgendamentoResponse> findAgendamentoById(Long id) {
        Optional<AgendamentoResponse> response = agendamentoService.getById(id);
        return response.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

}
