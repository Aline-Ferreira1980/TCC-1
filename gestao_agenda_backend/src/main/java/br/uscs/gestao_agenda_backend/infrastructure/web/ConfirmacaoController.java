package br.uscs.gestao_agenda_backend.infrastructure.web;

import br.uscs.gestao_agenda_backend.application.port.ConfirmacaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("confirm")
@Tag(name = "Confirmação")
public class ConfirmacaoController {

    private final ConfirmacaoService confirmacaoService;

    @Operation(summary = "Confirmação de e-mail cadastrado via token", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Confirmação realizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Token nao encontrados"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar busca dos dados"),
    })
    @GetMapping
    public ResponseEntity<String> findAgendamentoByUserId(@RequestParam("token") String token) {
        try {
            confirmacaoService.confirmarEmail(token);
            return ResponseEntity.ok("Usuário confirmado com sucesso");
        }catch (UsernameNotFoundException ex){
            return ResponseEntity.notFound().build();
        }
    }
}
