package br.uscs.gestao_agenda_backend.adapter.out.database.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "Paciente_Vs_Discente")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PacienteVsDiscenteEntity {

    @Id
    @Column(name = "id_paciente")
    @OneToOne(mappedBy = "Paciente")
    private PacienteEntity idPaciente;

    @Column(name = "ra_discente")
    @OneToOne(mappedBy = "Discente")
    private DiscenteEntity raDiscente;

}
