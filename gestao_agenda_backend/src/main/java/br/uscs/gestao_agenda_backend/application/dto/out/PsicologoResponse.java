package br.uscs.gestao_agenda_backend.application.dto.out;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class PsicologoResponse {
    private Long psicologoId;
    private String nome;
    private String especialidade;
    private String email;
    private List<HorarioTrabalhoResponse> horariosTrabalho;
}
