package br.uscs.gestao_agenda_backend.application.dto.out;


import br.uscs.gestao_agenda_backend.domain.model.Paciente;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class PacienteResponse {

    private String nome;
    private String email;
    private String queixa;



}
