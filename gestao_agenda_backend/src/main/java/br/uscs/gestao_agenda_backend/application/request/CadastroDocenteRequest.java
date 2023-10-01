package br.uscs.gestao_agenda_backend.application.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CadastroDocenteRequest {

    @NonNull
    @NotBlank
    @Email(message = "O e-mail deve estar no formato válido.")
    @Pattern(regexp = "^[a-zA-Z0-9_.+-]+@uscsonline\\.com\\.br$",
            message = "O e-mail deve estar no formato <usuário>@uscsonline.com.br")
    @Schema(example = "docente@uscsonline.com.br")
    private String email;

    @NonNull
    @NotBlank
    @Size(min = 6, message = "A senha deve conter pelo menos 6 caracteres.")
    private String senha;

    @NonNull
    @NotBlank
    private String nome;

    @NonNull
    @NotBlank
    private String sobrenome;

    @NonNull
    @NotBlank
    @Pattern(regexp = "^[0-9]{7}$", message = "O RUSCS deve conter exatamente 7 dígitos numéricos.")
    private String ruscs;


}
