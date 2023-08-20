package br.uscs.gestao_agenda_backend.adapter.out.database.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Time;

@Entity
@Table(name = "EspacoReservado")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EspacoReservadoEntity implements Serializable {

    @Id
    @Column(name = "id_paciente")
    private PacienteEntity idPaciente;
    @Column(name = "quem_contactou")
    private String quemContactou;
    @Column(name = "data_contato")
    @DateTimeFormat(pattern="yyyy-mm-dd")
    private DateTime data_contato;
    @Column(name = "horario_contato")
    private Time horarioContato;
    @Column(name = "observacao")
    private String observacao;
    @Column(name = "encaminhamento")
    private boolean encaminhamento;
    @Column(name = "servico_utilizado_paciente")
    private ServicoEntity servicoUtilizadoPaciente;

}
