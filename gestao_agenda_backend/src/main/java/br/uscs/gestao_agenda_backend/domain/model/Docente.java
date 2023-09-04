package br.uscs.gestao_agenda_backend.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Docente extends User {

    @Column(unique = true)
    private String ruscs;

    @ManyToMany(mappedBy = "docentes")
    private Set<Servico> servicos;

    @OneToMany(mappedBy = "professorResponsavel")
    private Set<Estagiario> estagiarios;

    @PreRemove
    private void preRemove() {
        estagiarios.forEach( child -> child.setProfessorResponsavel(null));
    }



}
