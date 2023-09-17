package br.uscs.gestao_agenda_backend.infrastructure.web;

import br.uscs.gestao_agenda_backend.application.request.CadastroEstagiarioRequest;
import br.uscs.gestao_agenda_backend.application.request.AtualizaEstagiarioRequest;
import br.uscs.gestao_agenda_backend.application.dto.EstagiarioResponse;
import br.uscs.gestao_agenda_backend.application.port.EstagiarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping(value = "estagiario", produces = {"application/json"})
@Tag(name = "Estagiario")
public class EstagiarioController {

    private final EstagiarioService estagiarioService;

    @GetMapping("/disponibilidade")
    public ResponseEntity<List<EstagiarioResponse>> getAvailabilityByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<EstagiarioResponse> response = estagiarioService.getAllAvailableInDataRange(startDate, endDate);
        return new ResponseEntity<List<EstagiarioResponse>>(response, HttpStatus.OK);
    }

    @Operation(summary = "Cadastra novo Estagiatio na aplicação", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Estagiario cadastrado com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "401", description = "Usuario nao autenticado"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar busca dos dados"),
    })
    @PostMapping(value = "/cadastrar", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EstagiarioResponse> cadastrarEstagiario(@RequestBody CadastroEstagiarioRequest request,
                                                                  UriComponentsBuilder uriBuilder) {
        EstagiarioResponse response = estagiarioService.cadastrarEstagiario(request);
        URI uri = uriBuilder.path("/transacoes/{id}").buildAndExpand(response.getId()).toUri();
        return ResponseEntity.created(uri).body(response);
    }

    @Operation(summary = "Busca Estagiatio por Id na aplicação", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estagiario cadastrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Estagiario nao encontrado"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "401", description = "Usuario nao autenticado"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar busca dos dados"),
    })
    @GetMapping(value = "/{id}")
    public ResponseEntity<EstagiarioResponse> getEstagiarioById(@PathVariable Long id){
        Optional<EstagiarioResponse> response = estagiarioService.findById(id);
        return response.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Lista todos os Estagiatios na aplicação", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estagiario cadastrado com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "401", description = "Usuario nao autenticado"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar busca dos dados"),
    })
    @GetMapping("/listar")
    public ResponseEntity<List<EstagiarioResponse>> getAllPsicologos() {
        List<EstagiarioResponse> response = estagiarioService.findAll();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Atualiza Estagiário na aplicação", method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estagiário atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Estagiário nao encontrado"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "401", description = "Usuário nao autenticado"),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar busca dos dados"),
    })
    @PutMapping("/{id}")
    public ResponseEntity<EstagiarioResponse> updateEstagiario(
            @PathVariable Long id,
            @RequestBody AtualizaEstagiarioRequest request){

        Optional<EstagiarioResponse> response = estagiarioService.updateEstagiario(id, request);
        return response.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Deleta Estagiário na aplicação", method = "DELETE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estagiário cadastrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Estagiário nao encontrado"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "401", description = "Usuário nao autenticado"),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar busca dos dados"),
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEstagiario(@PathVariable Long id){
        try {
            estagiarioService.deletaEstagiario(id);
            return ResponseEntity.ok("Estagiario deletado com sucesso");
        }catch (EmptyResultDataAccessException e){
            return ResponseEntity.notFound().build();
        }
    }


}
