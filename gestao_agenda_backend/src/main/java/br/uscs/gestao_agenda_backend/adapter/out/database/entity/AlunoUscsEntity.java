package br.uscs.gestao_agenda_backend.adapter.out.database.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "Aluno_Uscs")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlunoUscsEntity implements Serializable {

    @OneToOne
    @JoinColumn(name = "alunoUscs")
    private PacienteEntity idPaciente;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ra_aluno")
    private Integer alunUscsRa;

    @Column(name = "nome_aluno")
    private String nomeAluno;

    @Column(name = "campus")
    private String campus;

    @Column(name = "curso")
    private String curso;

    @Column(name = "periodo")
    private String periodo;

    @Column(name = "semestre")
    private String semestre;

}
