package br.uscs.gestao_agenda_backend.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Endereco {

    @NotNull
    @NotBlank(message = "O campo 'rua' é obrigatório.")
    private String rua;

    @NotNull
    @NotBlank(message = "O campo 'cidade' é obrigatório.")
    private String cidade;

    @NotNull
    @NotBlank(message = "O campo 'bairro' é obrigatório.")
    private String bairro;

    @NotNull
    @NotBlank(message = "O campo 'cep' é obrigatório.")
    @Pattern(regexp = "\\d{5}-\\d{3}", message = "O campo 'cep' deve estar no formato 'XXXXX-XXX'.")
    private String cep;
}
