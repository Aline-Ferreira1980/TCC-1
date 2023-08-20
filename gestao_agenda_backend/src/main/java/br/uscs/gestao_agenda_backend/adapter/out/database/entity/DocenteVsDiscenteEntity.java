package br.uscs.gestao_agenda_backend.adapter.out.database.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "Docente_Vs_Discente")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DocenteVsDiscenteEntity implements Serializable {

    @Id
    @ManyToOne
    @JoinColumn(name = "ra_discente")
    private DiscenteEntity raDiscente;

    @ManyToOne
    @JoinColumn(name = "ruscs_docente")
    private DocenteEntity ruscsDocente;

}
