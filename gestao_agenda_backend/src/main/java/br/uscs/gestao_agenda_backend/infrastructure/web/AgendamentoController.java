package br.uscs.gestao_agenda_backend.infrastructure.web;

import br.uscs.gestao_agenda_backend.application.common.AgendamentoMapper;
import br.uscs.gestao_agenda_backend.application.common.HorarioTrabalhoMapper;
import br.uscs.gestao_agenda_backend.application.dto.AgendamentoResponse;
import br.uscs.gestao_agenda_backend.application.port.AgendamentoService;
import br.uscs.gestao_agenda_backend.application.request.AgendamentoRequest;
import br.uscs.gestao_agenda_backend.domain.model.Agendamento;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
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
        } catch (Exception ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @Operation(summary = "Cria novo agendamento na aplicação", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Agendamento nao encontrados"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "401", description = "Usuario nao autenticado"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar busca dos dados"),
    })
    @GetMapping(value = "/user/{id}")
    public ResponseEntity<AgendamentoResponse> cadastrarPaciente(@PathVariable Long id) {
        Optional<AgendamentoResponse> response = agendamentoService.findAgendamentoByUserId(id);
        return response.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }




}
