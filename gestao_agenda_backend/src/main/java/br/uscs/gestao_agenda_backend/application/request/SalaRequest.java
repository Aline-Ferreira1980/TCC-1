package br.uscs.gestao_agenda_backend.application.request;

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
