package br.uscs.gestao_agenda_backend.application.dto.in;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CadastroPsicologoRequest {
    private String nome;
    private String email;
    private String senha;
    private String especialidade;
    private List<HorarioTrabalhoRequest> horariosTrabalho;
}
