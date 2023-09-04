package br.uscs.gestao_agenda_backend.application.request;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CadastroHorarioTrabalhoRequest {

    private Long psicologoId;
    private List<HorarioTrabalhoRequest> horariosTrabalho;

}
