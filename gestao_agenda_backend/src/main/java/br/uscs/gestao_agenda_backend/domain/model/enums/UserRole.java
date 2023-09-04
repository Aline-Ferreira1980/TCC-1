package br.uscs.gestao_agenda_backend.domain.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor
@Getter
public enum UserRole {
    PACIENTE("paciente"),
    ESTAGIARIO("estagiario");

    private final String role;

}
