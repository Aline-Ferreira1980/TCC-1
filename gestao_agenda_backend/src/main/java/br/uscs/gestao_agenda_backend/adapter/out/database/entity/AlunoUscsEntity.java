package br.uscs.gestao_agenda_backend.adapter.out.database.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "Aluno_Uscs")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlunoUscsEntity {

    @Id
    @Column(name = "id_paciente")
    private PacienteEntity idPaciente;
    @Column(name = "aluno_uscs")
    private String alunoUscs;
    @Column(name = "ra_aluno_uscs")
    private Integer alunUscsRa;
    @Column(name = "campus")
    private String campus;
    @Column(name = "curso")
    private String curso;
    @Column(name = "periodo")
    private String periodo;
    @Column(name = "semestre")
    private String semestre;

}
