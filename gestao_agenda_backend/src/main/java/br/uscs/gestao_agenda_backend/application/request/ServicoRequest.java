package br.uscs.gestao_agenda_backend.application.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ServicoRequest {

    @NotBlank
    @NotNull
    private String acronimo;
    @NotBlank
    @NotNull
    private String nome;
    @NotBlank
    @NotNull
    private String descricao;

//    @NotEmpty
//    private Set<EstagiarioPropertyResponse> estagiarios;
//
//    @NotEmpty
//    private Set<DocentePropertyResponse> docentes;

}
