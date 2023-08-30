package br.uscs.gestao_agenda_backend.application.dto.in;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CadastroPsicologoRequest {
    private String nome;
    private String email;
    private String senha;
    private String turno;
    private Date anoRef;
    private String semestreEmCurso;
    private String cursaPsico;
    private String orientador;
    private String especialidade;
    private List<HorarioTrabalhoRequest> horariosTrabalho;
}
