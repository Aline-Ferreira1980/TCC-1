package br.uscs.gestao_agenda_backend.infrastructure.web;

import br.uscs.gestao_agenda_backend.application.common.PacienteMapper;
import br.uscs.gestao_agenda_backend.application.dto.PacienteResponse;
import br.uscs.gestao_agenda_backend.application.port.PacienteService;
import br.uscs.gestao_agenda_backend.application.request.AtualizaPacienteRequest;
import br.uscs.gestao_agenda_backend.application.request.CadastroPacienteRequest;
import br.uscs.gestao_agenda_backend.infrastructure.security.permissions.CheckSecurity;
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
import javax.validation.constraints.NotNull;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "paciente", produces = {"application/json"})
@Tag(name = "Paciente")
@AllArgsConstructor
public class PacienteContoller {

    private PacienteService pacienteService;
    private final PacienteMapper pacienteMapper;

    @Operation(summary = "Cadastra novo paciente na aplicação", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Paciente cadastrado com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "401", description = "Usuario nao autenticado"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar busca dos dados"),
    })
    @PostMapping(value = "/cadastrar", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PacienteResponse> cadastrarPaciente(@Valid @RequestBody CadastroPacienteRequest request,
                                                              UriComponentsBuilder uriBuilder) {
        PacienteResponse response = pacienteService.cadastraPaciente(pacienteMapper.fromRequest(request));
        URI uri = uriBuilder.path("/paciente/{id}").buildAndExpand(response.getId()).toUri();
        return ResponseEntity.created(uri).body(response);
    }

    @Operation(summary = "Realiza a listagem de todos os pacientes cadastrados", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "401", description = "Usuario nao autenticado"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar busca dos dados"),
    })
    @CheckSecurity.User.CanViewAllPacientesProfiles
    @GetMapping("/listar")
    public ResponseEntity<List<PacienteResponse>> getAllPacientes() {
        List<PacienteResponse> response = pacienteService.findAll();
        return ResponseEntity.ok(response);
    }


    @Operation(summary = "Realiza busca pacientes por id", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "404", description = "Paciente no encontrado"),
            @ApiResponse(responseCode = "401", description = "Usuario nao autenticado"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar busca dos dados"),
    })
    @GetMapping(value = "/{id}")
    public ResponseEntity<PacienteResponse> getPaciente(
            @PathVariable
            @NotNull(message = "O parâmetro 'id' é obrigatório")
            @Min(value = 1, message = "O campo 'id' deve ser maior ou igual a 1.")
            Long id) {

        Optional<PacienteResponse> response = pacienteService.findById(id);
        return response.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Atualiza paciente na aplicação", method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Paciente Atualizado com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "401", description = "Usuario nao autenticado"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar atualização do Paciente"),
    })
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PacienteResponse> atualizaPaciente(
            @PathVariable
            @NotNull(message = "O parâmetro 'id' é obrigatório")
            @Min(value = 1, message = "O campo 'id' deve ser maior ou igual a 1.")
            Long id,
            @Valid
            @RequestBody AtualizaPacienteRequest request) {

        Optional<PacienteResponse> response = pacienteService.atualizaPaciente(id, pacienteMapper.fromRequest(request));
        return response.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Deleta paciente na aplicação", method = "DELETE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Paciente Deletado com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "401", description = "Usuario nao autenticado"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar atualização do Paciente"),
    })
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deletaPaciente(
            @PathVariable
            @NotNull(message = "O parâmetro 'id' é obrigatório")
            @Min(value = 1, message = "O campo 'id' deve ser maior ou igual a 1.")
            Long id) {

        try {
            pacienteService.deletaPaciente(id);
            return ResponseEntity.ok("Usuário deletado com sucesso");
        } catch (EmptyResultDataAccessException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
