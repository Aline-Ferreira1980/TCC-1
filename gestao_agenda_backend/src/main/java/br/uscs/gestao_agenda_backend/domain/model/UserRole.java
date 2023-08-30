package br.uscs.gestao_agenda_backend.domain.model;

public enum UserRole {
    PACIENTE("paciente"),
    PSICOLOGO("pscicologo"),
    DOCENTE_ORIENTADOR("professorOrientador");

    private final String role;

    UserRole(String role){
        this.role = role;
    }

    public String getRole(){
        return this.role;
    }
}
