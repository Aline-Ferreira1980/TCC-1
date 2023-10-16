package br.uscs.gestao_agenda_backend.domain.model;

import br.uscs.gestao_agenda_backend.domain.model.enums.Turno;
import lombok.*;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
public class Estagiario extends User {

    @Column(unique = true)
    private String ra;

    @Enumerated(EnumType.STRING)
    private Turno turno;

    private String turma;
    private Integer semestre;

    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "estagiario", cascade = CascadeType.ALL)
    private List<Paciente> pacientes;

    @EqualsAndHashCode.Exclude
    @ManyToMany(mappedBy = "estagiarios", cascade = CascadeType.ALL)
    private List<Servico> servicos;

    @ManyToOne
    private Docente professorResponsavel;

    @OneToMany(mappedBy = "estagiario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HorarioTrabalho> horariosTrabalho;

    @OneToMany(mappedBy = "estagiario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Agendamento> agendamentos;


    @PreRemove
    private void preRemove() {
        pacientes.forEach(child -> child.setEstagiario(null));
    }

    public void addAllHorarioTrabalho(List<HorarioTrabalho> horarios) {
        this.horariosTrabalho.addAll(horarios);
    }

    public boolean trabalhaNoDia(DayOfWeek diaDesejado) {
        return horariosTrabalho.stream()
                .anyMatch(horario -> horario.getDiaSemana() == diaDesejado);
    }

    public boolean trabalhaNoRangeDeHorario(LocalTime inicio, LocalTime fim) {
        boolean iniciaDepois =
                horariosTrabalho.stream().anyMatch(horario -> horario.getHorarioInicio().isBefore(inicio));

        boolean terminaAntes =
                horariosTrabalho.stream().anyMatch(horario -> horario.getHorarioFim().isAfter(fim));

        return iniciaDepois && terminaAntes;
    }

    public void addPaciente(Paciente paciente) {
        for(Paciente p : this.pacientes){
            if(paciente.getId().equals(p.getId())){
                throw new EntityExistsException("Paciente informado ja esta registrado a este estagiario");
            }
        }
        this.pacientes.add(paciente);
    }
}
