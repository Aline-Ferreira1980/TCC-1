package br.uscs.gestao_agenda_backend.domain.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Getter
public enum EstadoCivil {

    SOLTEIRO("solteiro"),
    CASADO("casado"),
    SEPARADO("separado"),
    DIVORCIADO("divorciado"),
    VIUVO ("viuvo"),
    UNIAO_ESTAVEL ("uniao_estavel");

    private final String estadoCivil;

}
