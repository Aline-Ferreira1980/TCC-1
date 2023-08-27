package br.uscs.gestao_agenda_backend.domain.model;


import lombok.*;

import javax.persistence.Entity;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Paciente extends User {

    private String queixa;

    @Builder
    public Paciente(String nome, String email, String senha, String queixa, UserRole role){
        super(nome, email, senha, role);
        this.queixa = queixa;
    }

}
