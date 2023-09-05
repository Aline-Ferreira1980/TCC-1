package br.uscs.gestao_agenda_backend.domain.model;

import br.uscs.gestao_agenda_backend.domain.model.enums.Turno;
import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
public class Estagiario extends User{

    private String ra;

    @Enumerated(EnumType.STRING)
    private Turno turno;

    private String turma;
    private Integer semestre;

    @OneToMany(mappedBy = "estagiario")
    private Set<Paciente> pacientes;

    @ManyToMany(mappedBy = "estagiarios")
    private Set<Servico> servicos;

    @ManyToOne
    private Docente professorResponsavel;

    @OneToMany(mappedBy = "estagiario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HorarioTrabalho> horariosTrabalho;

    @OneToMany(mappedBy = "estagiario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Agendamento> agendamentos;


    @PreRemove
    private void preRemove() {
        pacientes.forEach( child -> child.setEstagiario(null));
    }

    public void addAllHorarioTrabalho(List<HorarioTrabalho> horarios){
        this.horariosTrabalho.addAll(horarios);
    }

}
