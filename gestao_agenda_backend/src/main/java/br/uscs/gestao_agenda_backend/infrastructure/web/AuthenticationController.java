package br.uscs.gestao_agenda_backend.infrastructure.web;


import br.uscs.gestao_agenda_backend.application.request.AuthenticationRequest;
import br.uscs.gestao_agenda_backend.application.dto.LoginResponse;
import br.uscs.gestao_agenda_backend.domain.model.User;
import br.uscs.gestao_agenda_backend.infrastructure.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid AuthenticationRequest request){
        UsernamePasswordAuthenticationToken usernamePassword =
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getSenha());
        Authentication auth = authenticationManager.authenticate(usernamePassword);

        String token = tokenService.generateToken((User) auth.getPrincipal());

        return new ResponseEntity<LoginResponse>(new LoginResponse(token), HttpStatus.OK);
    }
}
