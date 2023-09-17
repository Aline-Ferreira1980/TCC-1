package br.uscs.gestao_agenda_backend.application.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CadastroDocenteRequest {

    @NonNull
    @NotBlank
    @Email
    private String email;

    @NonNull
    @NotBlank
    private String senha;

    @NonNull
    @NotBlank
    private String nome;

    @NonNull
    @NotBlank
    private String sobrenome;


    @NonNull
    @NotBlank
    private String ruscs;

}
