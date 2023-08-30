package br.uscs.gestao_agenda_backend.application.dto.out;


import br.uscs.gestao_agenda_backend.application.dto.in.Endereco;
import br.uscs.gestao_agenda_backend.application.dto.in.Telefone;
import br.uscs.gestao_agenda_backend.domain.model.Paciente;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class PacienteResponse {

    private String nomeRegistro;
    private String nomeSocial;
    private Date dataNascimento;
    private String genero;
    private String estadoCivil;
    private String etniaRacial;
    private List<Endereco> endereco;
    private List<Telefone> telefone;
    private boolean portadorDeficiencia;

}
