package br.uscs.gestao_agenda_backend.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Agendamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Estagiario estagiario;

    @ManyToOne
    private Paciente paciente;

    @ManyToOne
    private Sala sala;

    private LocalDateTime inicioAgendamento;

    private LocalDateTime fimAgendamento;

}
