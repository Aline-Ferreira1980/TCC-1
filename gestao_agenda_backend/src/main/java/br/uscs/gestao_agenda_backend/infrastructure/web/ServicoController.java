package br.uscs.gestao_agenda_backend.infrastructure.web;

import br.uscs.gestao_agenda_backend.application.dto.AgendamentoResponse;
import br.uscs.gestao_agenda_backend.application.dto.ServicoResponse;
import br.uscs.gestao_agenda_backend.application.request.AgendamentoRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("servico")
@Tag(name = "Serviço")
public class ServicoController {

    @Operation(summary = "Cria novo serviço na aplicação", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Serviço criado com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "401", description = "Usuário nao autenticado"),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar busca dos dados"),
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> cadastrarPaciente(@RequestBody ServicoRequest request,
                                               UriComponentsBuilder uriBuilder) {

        try {
            Optional<ServicoResponse> response = servicoService.cadastrarServico(request);
            if (response.isPresent()) {

                URI uri = uriBuilder.path("/{id}").buildAndExpand(response.get().getId()).toUri();
                return ResponseEntity.created(uri).body(response.get());
            }
            return ResponseEntity.badRequest().build();
        } catch (Exception ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
}
