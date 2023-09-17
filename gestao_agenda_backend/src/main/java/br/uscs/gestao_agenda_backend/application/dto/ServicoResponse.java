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
public class ServicoResponse {

    private Long id;

    private String acronimo;
    private String nome;
    private String descricao;
    private Set<EstagiarioPropertyResponse> estagiarios;
    private Set<DocentePropertyResponse> docentes;

}
