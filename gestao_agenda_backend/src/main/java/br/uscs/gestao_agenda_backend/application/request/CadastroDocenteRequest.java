package br.uscs.gestao_agenda_backend.application.request;

import br.uscs.gestao_agenda_backend.domain.model.Estagiario;
import br.uscs.gestao_agenda_backend.domain.model.Servico;
import br.uscs.gestao_agenda_backend.domain.model.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Set;

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

    private UserRole role;

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
