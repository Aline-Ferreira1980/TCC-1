package br.uscs.gestao_agenda_backend.infrastructure.web;

import br.uscs.gestao_agenda_backend.application.dto.ServicoResponse;
import br.uscs.gestao_agenda_backend.application.port.ServicoService;
import br.uscs.gestao_agenda_backend.application.request.ServicoRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("servico")
@Tag(name = "Serviço")
public class ServicoController {

    private final ServicoService servicoService;

    @Operation(summary = "Cria novo serviço na aplicação", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Serviço criado com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "401", description = "Usuário nao autenticado"),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar busca dos dados"),
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ServicoResponse> cadastrarSerico(@Valid @RequestBody ServicoRequest request,
                                                           UriComponentsBuilder uriBuilder) {

        try {
            Optional<ServicoResponse> response = servicoService.cadastrarServico(request);
            if (response.isPresent()) {

                URI uri = uriBuilder.path("/{id}").buildAndExpand(response.get().getId()).toUri();
                return ResponseEntity.created(uri).body(response.get());
            }
            return ResponseEntity.badRequest().build();
        } catch (Exception ex) {
            // TODO: Levantar um erro especifico
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "Realiza busca serviço por id", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "404", description = "Docente não encontrado"),
            @ApiResponse(responseCode = "401", description = "Usuário nao autenticado"),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar busca dos dados"),
    })
    @GetMapping(value = "/{id}")
    public ResponseEntity<ServicoResponse> getServicoById(
            @PathVariable
            @NotNull(message = "O parâmetro 'id' é obrigatório")
            @Min(value = 1, message = "O campo 'id' deve ser maior ou igual a 1.")
            Long id) {

        Optional<ServicoResponse> response = servicoService.findById(id);
        return response.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }


    @Operation(summary = "Lista todos os serviços cadastrados na aplicação", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "404", description = "Docente não encontrado"),
            @ApiResponse(responseCode = "401", description = "Usuário nao autenticado"),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar busca dos dados"),
    })
    @GetMapping(value = "/listar")
    public ResponseEntity<List<ServicoResponse>> getAllServico() {
        List<ServicoResponse> response = servicoService.findAll();
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Atualiza Serviço na aplicação", method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Serviço atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Serviço nao encontrado"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "401", description = "Usuário nao autenticado"),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar busca dos dados"),
    })
    @PutMapping("/{id}")
    public ResponseEntity<ServicoResponse> updateServico(
            @PathVariable
            @NotNull(message = "O parâmetro 'id' é obrigatório")
            @Min(value = 1, message = "O campo 'id' deve ser maior ou igual a 1.")
            Long id,
            @Valid
            @RequestBody ServicoRequest request) {

        Optional<ServicoResponse> response = servicoService.udpateServico(id, request);
        return response.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Deleta serviço da aplicação", method = "DELETE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Serviço removido com sucesso"),
            @ApiResponse(responseCode = "404", description = "Serviço nao encontrado"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "401", description = "Usuário nao autenticado"),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar busca dos dados"),
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteServico(
            @PathVariable
            @NotNull(message = "O parâmetro 'id' é obrigatório")
            @Min(value = 1, message = "O campo 'id' deve ser maior ou igual a 1.")
            Long id) {

        try {
            servicoService.deletaServico(id);
            return ResponseEntity.ok("Serviço deletado com sucesso");
        } catch (EmptyResultDataAccessException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Vincula estagiário a serviço", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estagiário vinculado com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "401", description = "Usuário nao autenticado"),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar busca dos dados"),
    })
    @PostMapping(value = "/{id_servico}/add_estagiario", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<ServicoResponse> addEstagiarioToService(
            @PathVariable
            @NotNull(message = "O parâmetro 'id_servico' é obrigatório")
            @Min(value = 1, message = "O campo 'id_servico' deve ser maior ou igual a 1.")
            Long id_servico,
            @RequestParam
            @NotNull(message = "O parâmetro 'id_estagiario' é obrigatório")
            @Min(value = 1, message = "O campo 'id_estagiario' deve ser maior ou igual a 1.")
            Long id_estagiario) {

        Optional<ServicoResponse> response = servicoService.addEstagiarioToServico(
                id_servico, id_estagiario);

        return response.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());

    }

    @Operation(summary = "Remove serviço de estagiaio na aplicação", method = "DELETE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estagiário cadastrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Estagiário nao encontrado"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "401", description = "Usuário nao autenticado"),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar busca dos dados"),
    })
    @DeleteMapping("{id_servico}/remove_estagiario")
    public ResponseEntity<ServicoResponse> removeServicoFromEstagiario(
            @PathVariable
            @NotNull(message = "O parâmetro 'id_servico' é obrigatório")
            @Min(value = 1, message = "O campo 'id_servico' deve ser maior ou igual a 1.")
            Long id_servico,
            @RequestParam
            @NotNull(message = "O parâmetro 'id_estagiario' é obrigatório")
            @Min(value = 1, message = "O campo 'id_estagiario' deve ser maior ou igual a 1.")
            Long id_estagiario) {

        Optional<ServicoResponse> response = servicoService.removeEstagiarioFromServico(id_servico, id_estagiario);
        return response.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Vincula docente a serviço", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estagiário vinculado com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "401", description = "Usuário nao autenticado"),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar busca dos dados"),
    })
    @PostMapping(value="/{id_servico}/add_docente", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<ServicoResponse> addDocenteToService(
            @PathVariable
            @NotNull(message = "O parâmetro 'id_servico' é obrigatório")
            @Min(value = 1, message = "O campo 'id_servico' deve ser maior ou igual a 1.")
            Long id_servico,
            @RequestParam
            @NotNull(message = "O parâmetro 'id_docente' é obrigatório")
            @Min(value = 1, message = "O campo 'id_docente' deve ser maior ou igual a 1.")
            Long id_docente) {

        Optional<ServicoResponse> response = servicoService.addDocenteToServico(
                id_servico, id_docente);

        return response.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());

    }

    @Operation(summary = "Remove serviço de Docente na aplicação", method = "DELETE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estagiário cadastrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Estagiário nao encontrado"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "401", description = "Usuário nao autenticado"),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar busca dos dados"),
    })
    @DeleteMapping("{id_servico}/remove_docente")
    public ResponseEntity<ServicoResponse> removeServicoFromDocente(
            @PathVariable
            @NotNull(message = "O parâmetro 'id_servico' é obrigatório")
            @Min(value = 1, message = "O campo 'id_servico' deve ser maior ou igual a 1.")
            Long id_servico,
            @RequestParam
            @NotNull(message = "O parâmetro 'id_docente' é obrigatório")
            @Min(value = 1, message = "O campo 'id_docente' deve ser maior ou igual a 1.")
            Long id_docente) {

        Optional<ServicoResponse> response = servicoService.removeDocenteFromServico(id_servico, id_docente);
        return response.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Vincula Lista de estagiários a serviço", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estagiário vinculado com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "401", description = "Usuário nao autenticado"),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar busca dos dados"),
    })
    @PostMapping(value = "/{id_servico}/add_estagiarios")
    public ResponseEntity<ServicoResponse> addEstagiariosToService(
            @PathVariable
            @NotNull(message = "O parâmetro 'id_servico' é obrigatório")
            @Min(value = 1, message = "O campo 'id_servico' deve ser maior ou igual a 1.")
            Long id_servico,
            @NotEmpty
            @RequestBody List<Long> id_estagiarios) {

        Optional<ServicoResponse> response = servicoService.addEstagiariosToServico(
                id_servico, id_estagiarios);

        return response.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());

    }

    @Operation(summary = "Vincula Lista de Docentes a serviço", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estagiário vinculado com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "401", description = "Usuário nao autenticado"),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar busca dos dados"),
    })
    @PostMapping(value = "/{id_servico}/add_docentes")
    public ResponseEntity<ServicoResponse> addDocentesToService(
            @PathVariable
            @NotNull(message = "O parâmetro 'id_servico' é obrigatório")
            @Min(value = 1, message = "O campo 'id_servico' deve ser maior ou igual a 1.")
            Long id_servico,
            @NotEmpty
            @RequestBody List<Long> id_docentes) {

        Optional<ServicoResponse> response = servicoService.addDocentesToServico(
                id_servico, id_docentes);

        return response.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());

    }

}