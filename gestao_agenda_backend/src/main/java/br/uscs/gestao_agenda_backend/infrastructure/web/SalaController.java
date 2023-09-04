package br.uscs.gestao_agenda_backend.infrastructure.web;

import br.uscs.gestao_agenda_backend.application.request.SalaRequest;
import br.uscs.gestao_agenda_backend.application.port.SalaService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("sala")
//@SecurityRequirement(name = "BearerAuth")
public class SalaController {

    @Autowired
    private SalaService salaService;

    @PostMapping("/cadastrar")
    public ResponseEntity<String> cadastrarSala(@RequestBody SalaRequest request){
        salaService.cadastraSala(request);
        return new ResponseEntity<>("Sala cadastrada com sucesso.", HttpStatus.CREATED);
    }
}
