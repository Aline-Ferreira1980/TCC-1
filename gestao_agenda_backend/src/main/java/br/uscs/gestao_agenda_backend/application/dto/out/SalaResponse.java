package br.uscs.gestao_agenda_backend.application.dto.out;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class SalaResponse {
    private String nome;
    private String local;

}
