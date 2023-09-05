package br.uscs.gestao_agenda_backend.application.request;

import br.uscs.gestao_agenda_backend.domain.model.enums.Turno;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
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

    @NonNull
    @NotBlank
    private String senha;

    @NonNull
    @NotBlank
    @Schema(example = "11118899")
    private String ra;

    @NonNull
    private Turno turno;

    @NonNull
    @NotBlank
    private String turma;

    @NonNull
    private Integer semestre;

    private List<HorarioTrabalhoRequest> horariosTrabalho;
}
