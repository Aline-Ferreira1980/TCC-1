package br.uscs.gestao_agenda_backend.application.dto;

import br.uscs.gestao_agenda_backend.domain.model.enums.Turno;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

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
