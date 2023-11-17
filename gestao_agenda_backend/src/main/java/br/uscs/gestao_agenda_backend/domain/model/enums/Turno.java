package br.uscs.gestao_agenda_backend.domain.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Turno {
    NOTURNO("noturno"),
    MATUTINO("matutino"),
    VESPERTINO("vespertino");

    private final String turno;

}
