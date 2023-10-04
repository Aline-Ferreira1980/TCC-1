package br.uscs.gestao_agenda_backend.infrastructure.web.openapi;

import br.uscs.gestao_agenda_backend.application.dto.ServicoResponse;
import br.uscs.gestao_agenda_backend.application.request.ServicoRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Tag(name = "Serviço")
@SecurityRequirement(name = "OAuth2")
public interface ServicoControllerOpenApi {
    @Operation(summary = "Cria novo serviço na aplicação", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Serviço criado com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "401", description = "Usuário nao autenticado"),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar busca dos dados"),
    })
    ResponseEntity<ServicoResponse> cadastrarSerico(@Valid @RequestBody ServicoRequest request,
                                                    UriComponentsBuilder uriBuilder);

    @Operation(summary = "Realiza busca serviço por id", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "404", description = "Docente não encontrado"),
            @ApiResponse(responseCode = "401", description = "Usuário nao autenticado"),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar busca dos dados"),
    })
    ResponseEntity<ServicoResponse> getServicoById(
            @PathVariable
            @NotNull(message = "O parâmetro 'id' é obrigatório")
            @Min(value = 1, message = "O campo 'id' deve ser maior ou igual a 1.")
            Long id);

    @Operation(summary = "Lista todos os serviços cadastrados na aplicação", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "404", description = "Docente não encontrado"),
            @ApiResponse(responseCode = "401", description = "Usuário nao autenticado"),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar busca dos dados"),
    })
    ResponseEntity<List<ServicoResponse>> getAllServico();

    @Operation(summary = "Atualiza Serviço na aplicação", method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Serviço atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Serviço nao encontrado"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "401", description = "Usuário nao autenticado"),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar busca dos dados"),
    })
    ResponseEntity<ServicoResponse> updateServico(
            @PathVariable
            @NotNull(message = "O parâmetro 'id' é obrigatório")
            @Min(value = 1, message = "O campo 'id' deve ser maior ou igual a 1.")
            Long id,
            @Valid
            @RequestBody ServicoRequest request);

    @Operation(summary = "Deleta serviço da aplicação", method = "DELETE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Serviço removido com sucesso"),
            @ApiResponse(responseCode = "404", description = "Serviço nao encontrado"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "401", description = "Usuário nao autenticado"),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar busca dos dados"),
    })
    ResponseEntity<String> deleteServico(
            @PathVariable
            @NotNull(message = "O parâmetro 'id' é obrigatório")
            @Min(value = 1, message = "O campo 'id' deve ser maior ou igual a 1.")
            Long id);

    @Operation(summary = "Vincula estagiário a serviço", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estagiário vinculado com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "401", description = "Usuário nao autenticado"),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar busca dos dados"),
    })
    ResponseEntity<ServicoResponse> addEstagiarioToService(
            @PathVariable
            @NotNull(message = "O parâmetro 'id_servico' é obrigatório")
            @Min(value = 1, message = "O campo 'id_servico' deve ser maior ou igual a 1.")
            Long id_servico,
            @RequestParam
            @NotNull(message = "O parâmetro 'id_estagiario' é obrigatório")
            @Min(value = 1, message = "O campo 'id_estagiario' deve ser maior ou igual a 1.")
            Long id_estagiario);

    @Operation(summary = "Remove serviço de estagiaio na aplicação", method = "DELETE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estagiário cadastrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Estagiário nao encontrado"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "401", description = "Usuário nao autenticado"),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar busca dos dados"),
    })
    ResponseEntity<ServicoResponse> removeServicoFromEstagiario(
            @PathVariable
            @NotNull(message = "O parâmetro 'id_servico' é obrigatório")
            @Min(value = 1, message = "O campo 'id_servico' deve ser maior ou igual a 1.")
            Long id_servico,
            @RequestParam
            @NotNull(message = "O parâmetro 'id_estagiario' é obrigatório")
            @Min(value = 1, message = "O campo 'id_estagiario' deve ser maior ou igual a 1.")
            Long id_estagiario);

    @Operation(summary = "Vincula docente a serviço", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estagiário vinculado com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "401", description = "Usuário nao autenticado"),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar busca dos dados"),
    })
    ResponseEntity<ServicoResponse> addDocenteToService(
            @PathVariable
            @NotNull(message = "O parâmetro 'id_servico' é obrigatório")
            @Min(value = 1, message = "O campo 'id_servico' deve ser maior ou igual a 1.")
            Long id_servico,
            @RequestParam
            @NotNull(message = "O parâmetro 'id_docente' é obrigatório")
            @Min(value = 1, message = "O campo 'id_docente' deve ser maior ou igual a 1.")
            Long id_docente);

    @Operation(summary = "Remove serviço de Docente na aplicação", method = "DELETE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estagiário cadastrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Estagiário nao encontrado"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "401", description = "Usuário nao autenticado"),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar busca dos dados"),
    })
    ResponseEntity<ServicoResponse> removeServicoFromDocente(
            @PathVariable
            @NotNull(message = "O parâmetro 'id_servico' é obrigatório")
            @Min(value = 1, message = "O campo 'id_servico' deve ser maior ou igual a 1.")
            Long id_servico,
            @RequestParam
            @NotNull(message = "O parâmetro 'id_docente' é obrigatório")
            @Min(value = 1, message = "O campo 'id_docente' deve ser maior ou igual a 1.")
            Long id_docente);

    @Operation(summary = "Vincula Lista de estagiários a serviço", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estagiário vinculado com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "401", description = "Usuário nao autenticado"),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar busca dos dados"),
    })
    ResponseEntity<ServicoResponse> addEstagiariosToService(
            @PathVariable
            @NotNull(message = "O parâmetro 'id_servico' é obrigatório")
            @Min(value = 1, message = "O campo 'id_servico' deve ser maior ou igual a 1.")
            Long id_servico,
            @NotEmpty
            @RequestBody List<Long> id_estagiarios);

    @Operation(summary = "Vincula Lista de Docentes a serviço", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estagiário vinculado com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "401", description = "Usuário nao autenticado"),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar busca dos dados"),
    })
    ResponseEntity<ServicoResponse> addDocentesToService(
            @PathVariable
            @NotNull(message = "O parâmetro 'id_servico' é obrigatório")
            @Min(value = 1, message = "O campo 'id_servico' deve ser maior ou igual a 1.")
            Long id_servico,
            @NotEmpty
            @RequestBody List<Long> id_docentes);
}
