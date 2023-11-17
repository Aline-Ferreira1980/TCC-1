package br.uscs.gestao_agenda_backend.domain.model;

import br.uscs.gestao_agenda_backend.application.request.validation.Enum;
import br.uscs.gestao_agenda_backend.domain.model.enums.TipoTelefone;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Pattern;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Telefone {

    @Enumerated(EnumType.STRING)
    @Enum(enumClass = TipoTelefone.class, message = "O valor 'tipo' de telefone nao é valido")
    private TipoTelefone tipo;

    @Pattern(regexp = "^\\(\\d{2}\\)\\d{5}-\\d{4}|^\\(\\d{2}\\)\\d{4}-\\d{4}$",
            message = "O campo 'telefone' deve estar no formato '(dd)nnnnn-nnnn' ou '(dd)nnnn-nnnn'.")
    @Schema(example = "(11)95555-6666 | (11)5555-6666")
    private String telefone;

}
