package br.uscs.gestao_agenda_backend.application.request;

import br.uscs.gestao_agenda_backend.application.request.validation.Enum;
import br.uscs.gestao_agenda_backend.domain.model.enums.Turno;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.validation.constraints.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AtualizaEstagiarioRequest {

    @NonNull
    @NotBlank
    private String nome;

    @NonNull
    @NotBlank
    private String sobrenome;

//    @NonNull
//    @NotBlank
//    @Size(min = 6, message = "A senha deve conter pelo menos 6 caracteres.")
//    private String senha;

    @NonNull
    @NotBlank
    @Pattern(regexp = "^[0-9]{7}$", message = "O RA deve conter exatamente 7 dígitos numéricos.")
    @Schema(example = "11118899")
    private String ra;

    @NonNull
    @Enum(enumClass = Turno.class, message = "O campo 'turno' não é válido.")
    private Turno turno;

    @NonNull
    @NotBlank
    private String turma;

    @NonNull
    @Min(value = 1, message = "O campo 'semestre' deve ser maior ou igual a 1.")
    @Max(value = 10, message = "O campo 'semestre' deve ser meno ou igual a 10.")
    private Integer semestre;

    @NotEmpty
    private List<HorarioTrabalhoRequest> horariosTrabalho;
}
