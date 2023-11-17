package br.uscs.gestao_agenda_backend.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class EstagiarioPropertyResponse {
    private Long id;
    private String nome;
    private String sobrenome;
    private String email;
    private String ra;
    private String turma;


}
