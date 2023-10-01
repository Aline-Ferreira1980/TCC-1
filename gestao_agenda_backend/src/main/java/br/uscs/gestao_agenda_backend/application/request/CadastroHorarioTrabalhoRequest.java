package br.uscs.gestao_agenda_backend.application.request;


import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CadastroHorarioTrabalhoRequest {

    @NonNull
    @Min(value = 1, message = "O campo 'psicologoId' deve ser maior ou igual a 1.")
    private Long psicologoId;

    @NotEmpty
    private List<HorarioTrabalhoRequest> horariosTrabalho;

}
