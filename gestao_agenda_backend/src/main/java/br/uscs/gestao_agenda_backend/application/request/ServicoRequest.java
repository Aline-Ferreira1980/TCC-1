package br.uscs.gestao_agenda_backend.application.request;

import br.uscs.gestao_agenda_backend.application.dto.DocentePropertyResponse;
import br.uscs.gestao_agenda_backend.application.dto.EstagiarioPropertyResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Set;

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
