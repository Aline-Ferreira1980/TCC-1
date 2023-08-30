package br.uscs.gestao_agenda_backend.application.dto.in;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Telefone {

    private String telefoneCelular;
    private String telefoneFixo;
    private String telefoneEmergencia;
    private String nomeContatoEmergencia;
}
