package br.uscs.gestao_agenda_backend.infrastructure.web;

import br.uscs.gestao_agenda_backend.application.port.ConfirmacaoService;
import br.uscs.gestao_agenda_backend.infrastructure.web.openapi.ConfirmacaoControllerOpenApi;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@CrossOrigin
@RequestMapping("confirm")
public class ConfirmacaoController implements ConfirmacaoControllerOpenApi {

    private final ConfirmacaoService confirmacaoService;

    @Override
    @GetMapping
    public ResponseEntity<String> findAgendamentoByUserId(String token) {
        try {
            confirmacaoService.confirmarEmail(token);
            return ResponseEntity.ok("Usuário confirmado com sucesso");
        }catch (UsernameNotFoundException ex){
            return ResponseEntity.notFound().build();
        }
    }
}
