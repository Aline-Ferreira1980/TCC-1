package br.uscs.gestao_agenda_backend.domain.model;

import lombok.*;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
public class HorarioTrabalho {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Estagiario estagiario;

    @Enumerated(EnumType.ORDINAL)
    private DayOfWeek diaSemana;

    private LocalTime horarioInicio;
    private LocalTime horarioFim;

    public HorarioTrabalho(Estagiario psicologo,
                           DayOfWeek diaSemana,
                           LocalTime horarioInicio,
                           LocalTime horarioFim){

        this.estagiario = psicologo;
        this.diaSemana = diaSemana;
        this.horarioInicio = horarioInicio;
        this.horarioFim = horarioFim;
    }

}
