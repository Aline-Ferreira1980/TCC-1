package br.uscs.gestao_agenda_backend.application.dto.in;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Endereco {

    private String ruaNumero;
    private String bairro;
    private String cidade;
    private String estado;
    private String cep;
}
