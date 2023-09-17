package br.uscs.gestao_agenda_backend.infrastructure.web;

import br.uscs.gestao_agenda_backend.application.common.AgendamentoMapper;
import br.uscs.gestao_agenda_backend.application.dto.AgendamentoResponse;
import br.uscs.gestao_agenda_backend.application.port.AgendamentoService;
import br.uscs.gestao_agenda_backend.application.request.AgendamentoRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.format.annotation.DateTimeFormat;
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
@Tag(name = "Agendamento")
public class AgendamentoController {

    private final AgendamentoService agendamentoService;
    private AgendamentoMapper agendamentoMapper;

    @Operation(summary = "Cria novo agendamento na aplicação", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Agendamento criado com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "401", description = "Usuario nao autenticado"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar busca dos dados"),
    })
    @PostMapping(value = "/agendar", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> cadastrarPaciente(@RequestBody AgendamentoRequest request,
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

    @Operation(summary = "Busca agendamento pelo id do usuário", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Agendamento nao encontrados"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "401", description = "Usuário nao autenticado"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar busca dos dados"),
    })
    @GetMapping(value = "/user/{id}")
    public ResponseEntity<List<AgendamentoResponse>> findAgendamentoByUserId(@PathVariable Long id) {
        List<AgendamentoResponse> response = agendamentoService.findAgendamentosByUserId(id);
        return (response != null) ? ResponseEntity.ok(response) : ResponseEntity.notFound().build();

    }

    @Operation(summary = "Busca agendamento pelo id da sala", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Agendamento nao encontrados"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "401", description = "Usuário nao autenticado"),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar busca dos dados"),
    })
    @GetMapping(value = "/sala/{id}")
    public ResponseEntity<List<AgendamentoResponse>> findAgendamentoBySalaId(@PathVariable Long id) {
        List<AgendamentoResponse> response = agendamentoService.findAgendamentosBySalaId(id);
        return (response != null) ? ResponseEntity.ok(response) : ResponseEntity.notFound().build();
    }

    @Operation(summary = "Busca agendamento pelo range de data", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Agendamento nao encontrados"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "401", description = "Usuário nao autenticado"),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar busca dos dados"),
    })
    @GetMapping("/findByDate")
    public ResponseEntity<List<AgendamentoResponse>> findAgendamentoByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fim) {
        List<AgendamentoResponse> response = agendamentoService.findAgendamentosByDateRange(inicio, fim);
        return (response != null) ? ResponseEntity.ok(response) : ResponseEntity.notFound().build();
    }

    @Operation(summary = "Busca agendamento pelo ID do usuário e range de data", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Agendamento nao encontrados"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "401", description = "Usuário nao autenticado"),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar busca dos dados"),
    })
    @GetMapping("/user/{userId}/findByDate")
    public ResponseEntity<List<AgendamentoResponse>> findAgendamentoByUserIdAndDateRange(
            @PathVariable Long userId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fim) {
        List<AgendamentoResponse> response = agendamentoService
                .findAgendamentoByUserIdAndDateRange(userId, inicio, fim);

        return (response != null) ? ResponseEntity.ok(response) : ResponseEntity.notFound().build();
    }

    @Operation(summary = "Atualiza Agendamento na aplicação", method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Agendamento atualizado com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "401", description = "Usuário nao autenticado"),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar atualização do Docente"),
    })
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AgendamentoResponse> atualizaAgendamento(@PathVariable Long id,
                                                                   @RequestBody AgendamentoRequest request) {

        Optional<AgendamentoResponse> response = agendamentoService.atualizaAgendamento(id, request);
        return response.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Deleta agendamento na aplicação", method = "DELETE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Docente deletado com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "401", description = "Usuario nao autenticado"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar atualização do Docente"),
    })
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteAgendamento(@PathVariable Long id) {
        try {
            agendamentoService.deleteAgendamento(id);
            return ResponseEntity.ok("Agendamento deletado com sucesso");
        } catch (EmptyResultDataAccessException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
