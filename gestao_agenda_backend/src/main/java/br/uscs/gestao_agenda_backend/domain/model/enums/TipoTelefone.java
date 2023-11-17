package br.uscs.gestao_agenda_backend.domain.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TipoTelefone {
    CELULAR("celular"),
    RESIDENCIA("residencia"),
    COMERCIAL("comercial");

    private final String tipoTelefone;
}
