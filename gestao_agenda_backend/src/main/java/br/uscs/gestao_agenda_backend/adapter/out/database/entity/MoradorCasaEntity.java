package br.uscs.gestao_agenda_backend.adapter.out.database.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "Morador_Casa")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MoradorCasaEntity implements Serializable {

    @ManyToOne
    @JoinColumn(name = "moradoresCasa")
    private PacienteEntity idPaciente;
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "id_morador")
    private Integer idMorador;


    @Column(name = "nome_morador")
    private String nomeMorador;
    @Column(name = "idade_morador")
    private Integer idadeMorador;
    @Column(name = "parentesco_morador")
    private String parentescoMorador;

}
