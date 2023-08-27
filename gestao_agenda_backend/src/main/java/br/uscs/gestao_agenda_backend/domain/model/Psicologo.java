package br.uscs.gestao_agenda_backend.domain.model;

import lombok.*;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
public class Psicologo extends User{

    private String especialidade;


    @Builder
    public Psicologo(String nome, String email, String senha, String especialidade, UserRole role){
        super(nome,email, senha, role);
        this.especialidade = especialidade;
    }

    @OneToMany(mappedBy = "psicologo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HorarioTrabalho> horariosTrabalho;



}
