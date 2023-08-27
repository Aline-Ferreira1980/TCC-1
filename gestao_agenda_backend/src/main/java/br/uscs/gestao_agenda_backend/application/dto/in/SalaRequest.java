package br.uscs.gestao_agenda_backend.application.dto.in;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class SalaRequest {

    private String local;
    private String nome;
}
