package br.uscs.gestao_agenda_backend.application.request;

import br.uscs.gestao_agenda_backend.domain.model.enums.Turno;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CadastroEstagiarioRequest {

    private String nome;
    private String sobrenome;
    private String email;
    private String senha;
    private String ra;
    private Turno turno;
    private String turma;
    private Integer semestre;
    private List<HorarioTrabalhoRequest> horariosTrabalho;
}
