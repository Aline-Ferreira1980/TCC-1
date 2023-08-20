package br.uscs.gestao_agenda_backend.adapter.out.database.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "Agendamento")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AgendamentoEntity {

    @Id
    @Column(name = "id_docente")
    private Integer ruscsDocente;
    @Column(name = "id_discente")
    private Integer raDiscente;
    @Column(name = "sala")
    private Integer idSala;
    @Column(name = "id_paciente")
    private Integer idPaciente;
    @Column(name = "dia_mes_ano")
    @DateTimeFormat(pattern="yyyy-mm-dd")
    private Date diaMesAno;
    @Column(name = "hora")
    private String hora;

}
