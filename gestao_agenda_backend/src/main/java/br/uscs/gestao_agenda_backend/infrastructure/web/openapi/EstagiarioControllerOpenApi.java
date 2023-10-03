package br.uscs.gestao_agenda_backend.infrastructure.web.openapi;

import br.uscs.gestao_agenda_backend.application.dto.EstagiarioResponse;
import br.uscs.gestao_agenda_backend.application.request.AtualizaEstagiarioRequest;
import br.uscs.gestao_agenda_backend.application.request.CadastroEstagiarioRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Tag(name = "Estagiario")
public interface EstagiarioControllerOpenApi {

    ResponseEntity<List<EstagiarioResponse>> getAvailabilityByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate);

    @Operation(summary = "Cadastra novo Estagiatio na aplicação", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Estagiario cadastrado com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "401", description = "Usuario nao autenticado"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar busca dos dados"),
    })
    ResponseEntity<EstagiarioResponse> cadastrarEstagiario(
            @Valid @RequestBody CadastroEstagiarioRequest request,
            UriComponentsBuilder uriBuilder);

    @Operation(summary = "Busca Estagiatio por Id na aplicação", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estagiario cadastrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Estagiario nao encontrado"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "401", description = "Usuario nao autenticado"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar busca dos dados"),
    })
    ResponseEntity<EstagiarioResponse> getEstagiarioById(
            @NotNull(message = "O parâmetro 'id' é obrigatório")
            @Min(value = 1, message = "O campo 'id' deve ser maior ou igual a 1.")
            @PathVariable Long id);

    @Operation(summary = "Lista todos os Estagiatios na aplicação", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estagiario cadastrado com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "401", description = "Usuario nao autenticado"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar busca dos dados"),
    })
    ResponseEntity<List<EstagiarioResponse>> getAllEstagiarios();

    @Operation(summary = "Atualiza Estagiário na aplicação", method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estagiário atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Estagiário nao encontrado"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "401", description = "Usuário nao autenticado"),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar busca dos dados"),
    })
    ResponseEntity<EstagiarioResponse> updateEstagiario(
            @NotNull(message = "O parâmetro 'id' é obrigatório")
            @Min(value = 1, message = "O campo 'id' deve ser maior ou igual a 1.")
            @PathVariable Long id,
            @Valid
            @RequestBody AtualizaEstagiarioRequest request);

    @Operation(summary = "Deleta Estagiário na aplicação", method = "DELETE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estagiário deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Estagiário nao encontrado"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "401", description = "Usuário nao autenticado"),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar busca dos dados"),
    })
    ResponseEntity<String> deleteEstagiario(
            @PathVariable
            @NotNull(message = "O parâmetro 'id' é obrigatório")
            @Min(value = 1, message = "O campo 'id' deve ser maior ou igual a 1.")
            Long id);

    @Operation(summary = "Lista estagiários por serviço", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "404", description = "Servico não encontrado"),
            @ApiResponse(responseCode = "401", description = "Usuário nao autenticado"),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar busca dos dados"),
    })
    ResponseEntity<List<EstagiarioResponse>> listEstagiariosByServico(
            @RequestParam
            @NotNull(message = "O parâmetro 'acronimo' é obrigatório")
            @NotBlank(message = "O parâmetro 'acronimo' é obrigatório")
            String acronimo);

    @Operation(summary = "Vincula docente a estagiario", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Docente vinculado com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "401", description = "Usuario nao autenticado"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar busca dos dados"),
    })
    ResponseEntity<EstagiarioResponse> addDocenteToEstagiario(
            @NotNull(message = "O parâmetro 'idEstagiario' é obrigatório")
            @Min(value = 1, message = "O campo 'idEstagiario' deve ser maior ou igual a 1.")
            @PathVariable Long idEstagiario,
            @NotNull(message = "O parâmetro 'id_docente' é obrigatório")
            @Min(value = 1, message = "O campo 'id_docente' deve ser maior ou igual a 1.")
            @RequestParam(value = "id_docente") Long idDocente);

    @Operation(summary = "Remove docente de estagiario", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Docente vinculado com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "401", description = "Usuario nao autenticado"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar busca dos dados"),
    })
    ResponseEntity<EstagiarioResponse> removeDocenteFromEstagiario(
            @PathVariable
            @NotNull(message = "O parâmetro 'idEstagiario' é obrigatório")
            @Min(value = 1, message = "O campo 'idEstagiario' deve ser maior ou igual a 1.")
            Long idEstagiario);

    @Operation(summary = "Lista Estagiario com serviço vazio", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "401", description = "Usuário nao autenticado"),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar busca dos dados"),
    })
    ResponseEntity<List<EstagiarioResponse>> listEstagiarioServicoEmpty();

    @Operation(summary = "Lista Estagiario com Docente vazio", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "401", description = "Usuário nao autenticado"),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar busca dos dados"),
    })
    ResponseEntity<List<EstagiarioResponse>> listEstagiarioDocenteEmpty();
}
