package br.uscs.gestao_agenda_backend.adapter.out.database.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "Agendamento")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AgendamentoEntity implements Serializable {

    @Id
    @Column(name = "ruscs_docente")
    private Integer ruscsDocente;

    @ManyToOne
    @JoinColumn(name= "Paciente_Vs_Discente")
    private PacienteVsDiscenteEntity pacienteVsDiscente;

    @Column(name = "sala")
    private SalaEntity idSala;

    @Column(name = "dia_mes_ano")
    @DateTimeFormat(pattern="yyyy-mm-dd")
    private Date diaMesAno;
    
    @Column(name = "hora")
    private String hora;

}
