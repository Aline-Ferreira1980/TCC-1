package br.uscs.gestao_agenda_backend.application.dto.in;

import br.uscs.gestao_agenda_backend.domain.model.Paciente;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CadastroPacienteRequest {
    private String nome;
    private String email;
    private String senha;
    private String queixa;

}
