package br.uscs.gestao_agenda_backend.application.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AuthenticationRequest {
    String email;
    String senha;
}
