package br.uscs.gestao_agenda_backend.adapter.out.database.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.criteria.CriteriaBuilder;

@Entity
@Table(name = "Docente")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DocenteEntity {

    @Id
    @Column(name = "ruscs_docente")
    private Integer ruscsDocente;
    @Column(name = "id_servico")
    private ServicoEntity idServico;
    @Column(name = "ra_discente")
    private DiscenteEntity raDiscente;

}
