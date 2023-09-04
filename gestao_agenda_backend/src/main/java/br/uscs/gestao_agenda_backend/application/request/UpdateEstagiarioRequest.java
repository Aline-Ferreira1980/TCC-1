package br.uscs.gestao_agenda_backend.application.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdateEstagiarioRequest {
    private Long id;
    private String nome;
    private String especialidade;
    private List<HorarioTrabalhoRequest> horariosTrabalho;
}
