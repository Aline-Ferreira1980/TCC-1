package br.uscs.gestao_agenda_backend.infrastructure.web.openapi;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "Confirmação")
@SecurityRequirement(name = "OAuth2")
public interface ConfirmacaoControllerOpenApi {
    @Operation(summary = "Confirmação de e-mail cadastrado via token", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Confirmação realizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Token nao encontrados"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar busca dos dados"),
    })
    ResponseEntity<String> findAgendamentoByUserId(@RequestParam("token") String token);
}
