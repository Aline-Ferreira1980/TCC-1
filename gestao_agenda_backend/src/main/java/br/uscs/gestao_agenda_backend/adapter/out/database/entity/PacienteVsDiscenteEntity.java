package br.uscs.gestao_agenda_backend.adapter.out.database.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "Paciente_Vs_Discente")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PacienteVsDiscenteEntity implements Serializable {

    @Id
    @Column(name = "id_paciente")
    private PacienteEntity idPaciente;

    @Column(name = "ra_discente")
    private DiscenteEntity raDiscente;

}
