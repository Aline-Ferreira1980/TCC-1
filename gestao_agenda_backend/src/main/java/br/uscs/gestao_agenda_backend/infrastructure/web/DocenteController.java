package br.uscs.gestao_agenda_backend.infrastructure.web;

import br.uscs.gestao_agenda_backend.application.common.DocenteMapper;
import br.uscs.gestao_agenda_backend.application.dto.DocenteResponse;
import br.uscs.gestao_agenda_backend.application.port.DocenteService;
import br.uscs.gestao_agenda_backend.application.request.AtualizaDocenteRequest;
import br.uscs.gestao_agenda_backend.application.request.CadastroDocenteRequest;
import br.uscs.gestao_agenda_backend.infrastructure.security.permissions.CheckSecurity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@RestController
@AllArgsConstructor
@RequestMapping("docente")
@CrossOrigin
@Tag(name = "Docente")
@SecurityRequirement(name = "OAuth2")
public class DocenteController {

    private final DocenteService docenteService;
    private final DocenteMapper docenteMapper;

    @GetMapping("/contexto")
    public String method() {
        var sec =  SecurityContextHolder.getContext().getAuthentication();
        return sec.getName();
    }


    @Operation(summary = "Cadastra nova docente na aplicação", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Docente cadastrado com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "401", description = "Usuario nao autenticado"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar busca dos dados"),
    })
    @CheckSecurity.User.CanCreateAndDeleteDocente
    @PostMapping(value = "/cadastrar", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DocenteResponse> cadastrarDocente(@Valid @RequestBody CadastroDocenteRequest request,
                                                            UriComponentsBuilder uriBuilder) {
        DocenteResponse response = docenteService.cadastraDocente(docenteMapper.fromRequest(request));
        URI uri = uriBuilder.path("/docente/{id}").buildAndExpand(response.getId()).toUri();
        return ResponseEntity.created(uri).body(response);
    }

    @Operation(summary = "Realiza a listagem de todos as docentes cadastrados", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "401", description = "Usuario nao autenticado"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar busca dos dados"),
    })
    @GetMapping("/listar")
    public ResponseEntity<List<DocenteResponse>> getAllDocente() {
        List<DocenteResponse> response = docenteService.findAll();
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Realiza busca Docente por id", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "404", description = "Docente não encontrado"),
            @ApiResponse(responseCode = "401", description = "Usuario nao autenticado"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar busca dos dados"),
    })
    @GetMapping(value = "/{id}")
    public ResponseEntity<DocenteResponse> getDocenteById(
            @PathVariable
            @NotNull(message = "O parâmetro 'id' é obrigatório")
            @Min(value = 1, message = "O campo 'id' deve ser maior ou igual a 1.")
            Long id) {
        Optional<DocenteResponse> response = docenteService.findById(id);
        return response.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Atualiza Docente na aplicação", method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Docente Atualizado com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "401", description = "Usuario nao autenticado"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar atualização do Docente"),
    })
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DocenteResponse> atualizaDocente(
            @PathVariable
            @NotNull(message = "O parâmetro 'id' é obrigatório")
            @Min(value = 1, message = "O campo 'id' deve ser maior ou igual a 1.")
            Long id,
            @Valid
            @RequestBody AtualizaDocenteRequest request) {

        Optional<DocenteResponse> response = docenteService.atualizaDocente(id, docenteMapper.fromRequest(request));
        return response.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Deleta docente na aplicação", method = "DELETE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Docente deletado com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "401", description = "Usuario nao autenticado"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar atualização do Docente"),
    })
    @CheckSecurity.User.CanCreateAndDeleteDocente
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deletaDocente(
            @PathVariable
            @NotNull(message = "O parâmetro 'id' é obrigatório")
            @Min(value = 1, message = "O campo 'id' deve ser maior ou igual a 1.")
            Long id) {
        try {
            docenteService.deletaDocente(id);
            return ResponseEntity.ok("Docente deletado com sucesso");
        } catch (EmptyResultDataAccessException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Lista Docent por serviço", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "404", description = "Servico não encontrado"),
            @ApiResponse(responseCode = "401", description = "Usuário nao autenticado"),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar busca dos dados"),
    })
    @GetMapping("/servico")
    public ResponseEntity<List<DocenteResponse>> listDocenteByServico(
            @RequestParam
            @NotNull(message = "O parâmetro 'acronimo' é obrigatório")
            @NotBlank(message = "O parâmetro 'acronimo' é obrigatório")
            String acronimo) {
        List<DocenteResponse> response = docenteService.findByServico(acronimo);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Lista Docente com serviço vazio", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "401", description = "Usuário nao autenticado"),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar busca dos dados"),
    })
    @GetMapping("/servico_vazio")
    public ResponseEntity<List<DocenteResponse>> listDocenteServicoEmpty() {
        List<DocenteResponse> response = docenteService.findServicoEmpty();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
