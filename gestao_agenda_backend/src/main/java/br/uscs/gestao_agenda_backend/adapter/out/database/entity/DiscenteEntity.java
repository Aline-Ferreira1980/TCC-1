package br.uscs.gestao_agenda_backend.adapter.out.database.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "Discente")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DiscenteEntity implements Serializable {

    @Id
    @Column(name = "ra_discente")
    private Integer raDiscente;
    @Column(name = "nome_discente")
    private String nomeDiscente;
    @Column(name = "tipo_discente")
    private String tipoDicsente;
    @Column(name = "funcao_discente")
    private String funcaoDiscente;
    @Column(name = "expediente_horas")
    private String expedienteHoras;
    @Column(name = "turno")
    private String turno;
    @Column(name = "ano_referencia")
    private String anoRef;
    @Column(name = "semestre_em_curso")
    private String semestreEmCurso;
    @Column(name = "tipo_estagio")
    private String tipoEstagio;
    @Column(name = "cursa_psicologia")
    private boolean cursaPsicologia;

    @ManyToOne
    @JoinColumn(name = "ruscs_docente_professor_responsavel")
    private DocenteVsDiscenteEntity ruscsDocenteOrientador;

    @ManyToOne
    @JoinColumn(name = "ruscs_docente_professor_responsavel")
    private DocenteVsDiscenteEntity ruscsDocenteProfessorResponsavel;

}