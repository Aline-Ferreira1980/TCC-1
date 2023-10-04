package br.uscs.gestao_agenda_backend.infrastructure.web;

import br.uscs.gestao_agenda_backend.application.dto.AgendamentoResponse;
import br.uscs.gestao_agenda_backend.application.port.AgendamentoService;
import br.uscs.gestao_agenda_backend.application.request.AgendamentoRequest;
import br.uscs.gestao_agenda_backend.infrastructure.security.facade.AuthenticationFacade;
import br.uscs.gestao_agenda_backend.infrastructure.security.permissions.CheckSecurity;
import br.uscs.gestao_agenda_backend.infrastructure.web.openapi.AgendamentoControllerOpenApi;
import lombok.AllArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@CrossOrigin
@RequestMapping("agendamento")
public class AgendamentoController implements AgendamentoControllerOpenApi {

    private final AgendamentoService agendamentoService;
    private final AuthenticationFacade authenticationFacade;

    @Override
    @CheckSecurity.Agendamento.CanCreateAgendamento
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
    @CheckSecurity.Agendamento.CanViewSelfAgendamento
    @GetMapping(value = "/user/{id}")
    public ResponseEntity<List<AgendamentoResponse>> findAgendamentoByUserId(Long id) {
        List<AgendamentoResponse> response = agendamentoService.findAgendamentosByUserId(id);
        return (response != null) ? ResponseEntity.ok(response) : ResponseEntity.notFound().build();

    }

    @Override
    @CheckSecurity.Agendamento.CanViewAgendamento
    @GetMapping(value = "/sala/{id}")
    public ResponseEntity<List<AgendamentoResponse>> findAgendamentoBySalaId(Long id) {
        List<AgendamentoResponse> response = agendamentoService.findAgendamentosBySalaId(id);
        return (response != null) ? ResponseEntity.ok(response) : ResponseEntity.notFound().build();
    }

    @Override
    @CheckSecurity.Agendamento.CanViewAgendamento
    @GetMapping("/findByDate")
    public ResponseEntity<List<AgendamentoResponse>> findAgendamentoByDateRange(LocalDateTime inicio,
                                                                                LocalDateTime fim) {
        List<AgendamentoResponse> response = agendamentoService.findAgendamentosByDateRange(inicio, fim);
        return (response != null) ? ResponseEntity.ok(response) : ResponseEntity.notFound().build();
    }

    @Override
    @CheckSecurity.Agendamento.CanViewSelfAgendamento
    @GetMapping("/user/{userId}/findByDate")
    public ResponseEntity<List<AgendamentoResponse>> findAgendamentoByUserIdAndDateRange(Long userId,
                                                                                         LocalDateTime inicio,
                                                                                         LocalDateTime fim) {
        List<AgendamentoResponse> response = agendamentoService
                .findAgendamentoByUserIdAndDateRange(userId, inicio, fim);

        return (response != null) ? ResponseEntity.ok(response) : ResponseEntity.notFound().build();
    }

    @Override
    @PreAuthorize("isAuthenticated() and " +
            "(hasAnyAuthority('docente', 'estagiario') or " +
            "#request.estagiarioEmail == authentication.principal.usename)")
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AgendamentoResponse> atualizaAgendamento(Long id, AgendamentoRequest request) {

        Optional<AgendamentoResponse> response = agendamentoService.atualizaAgendamento(id, request);
        return response.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Override
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteAgendamento(Long id) {

        Authentication authentication = authenticationFacade.getAuthentication();
        Jwt jwt = (Jwt) authentication.getPrincipal();
        String email = jwt.getClaim("user_name");
        Long userId = jwt.getClaim("user_id");

        try {
            agendamentoService.deleteAgendamento(id);
            return ResponseEntity.ok("Agendamento deletado com sucesso");
        } catch (EmptyResultDataAccessException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
