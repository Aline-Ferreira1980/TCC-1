package br.uscs.gestao_agenda_backend.adapter.out.database.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Docente_Vs_Discente")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DocenteVsDiscenteEntity {

    @Id
    @Column(name = "ra_discente")
    private DiscenteEntity raDiscente;
    @Column(name = "ruscs_docente")
    private DocenteEntity ruscsDocente;

}
