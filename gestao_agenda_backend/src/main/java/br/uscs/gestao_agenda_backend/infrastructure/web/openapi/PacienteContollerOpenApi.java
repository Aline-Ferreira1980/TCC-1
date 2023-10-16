package br.uscs.gestao_agenda_backend.infrastructure.web.openapi;

import br.uscs.gestao_agenda_backend.application.dto.PacienteResponse;
import br.uscs.gestao_agenda_backend.application.request.AtualizaPacienteRequest;
import br.uscs.gestao_agenda_backend.application.request.CadastroPacienteRequest;
import br.uscs.gestao_agenda_backend.infrastructure.security.permissions.CheckSecurity;
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
import javax.validation.constraints.NotNull;
import java.util.List;

@Tag(name = "Paciente")
@SecurityRequirement(name = "OAuth2")
public interface PacienteContollerOpenApi {
    @Operation(summary = "Cadastra novo paciente na aplicação", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Paciente cadastrado com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "401", description = "Usuario nao autenticado"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar busca dos dados"),
    })
    ResponseEntity<PacienteResponse> cadastrarPaciente(@Valid @RequestBody CadastroPacienteRequest request,
                                                       UriComponentsBuilder uriBuilder);

    @Operation(summary = "Realiza a listagem de todos os pacientes cadastrados", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "401", description = "Usuario nao autenticado"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar busca dos dados"),
    })
    ResponseEntity<List<PacienteResponse>> getAllPacientes();

    @Operation(summary = "Realiza busca pacientes por id", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "404", description = "Paciente no encontrado"),
            @ApiResponse(responseCode = "401", description = "Usuario nao autenticado"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar busca dos dados"),
    })
    ResponseEntity<PacienteResponse> getPaciente(
            @PathVariable
            @NotNull(message = "O parâmetro 'id' é obrigatório")
            @Min(value = 1, message = "O campo 'id' deve ser maior ou igual a 1.")
            Long id);

    @Operation(summary = "Atualiza paciente na aplicação", method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Paciente Atualizado com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "401", description = "Usuario nao autenticado"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar atualização do Paciente"),
    })
    ResponseEntity<PacienteResponse> atualizaPaciente(
            @PathVariable
            @NotNull(message = "O parâmetro 'id' é obrigatório")
            @Min(value = 1, message = "O campo 'id' deve ser maior ou igual a 1.")
            Long id,
            @Valid
            @RequestBody AtualizaPacienteRequest request);

    @Operation(summary = "Deleta paciente na aplicação", method = "DELETE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Paciente Deletado com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "401", description = "Usuario nao autenticado"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar atualização do Paciente"),
    })
    ResponseEntity<String> deletaPaciente(
            @PathVariable
            @NotNull(message = "O parâmetro 'id' é obrigatório")
            @Min(value = 1, message = "O campo 'id' deve ser maior ou igual a 1.")
            Long id);

    ResponseEntity<List<PacienteResponse>> getPacientesByIdEstagiario(
            @RequestParam(value = "id_estagiario")
            @NotNull(message = "O parâmetro 'id' é obrigatório")
            @Min(value = 1, message = "O campo 'id' deve ser maior ou igual a 1.")
            Long id);
}
