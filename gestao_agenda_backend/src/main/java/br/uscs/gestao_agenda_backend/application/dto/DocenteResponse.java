package br.uscs.gestao_agenda_backend.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class DocenteResponse {
    private Long id;
    private String nome;
    private String sobrenome;
    private String email;
    private String ruscs;
    private Set<ServicoPropertyResponse> servicos;
    private Set<EstagiarioPropertyResponse> estagiarios;
}
