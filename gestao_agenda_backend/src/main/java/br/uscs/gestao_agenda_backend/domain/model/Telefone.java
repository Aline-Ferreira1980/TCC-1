package br.uscs.gestao_agenda_backend.domain.model;

import br.uscs.gestao_agenda_backend.domain.model.enums.TipoTelefone;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Telefone {

    @Enumerated(EnumType.STRING)
    private TipoTelefone tipo;
    private String telefone;

}
