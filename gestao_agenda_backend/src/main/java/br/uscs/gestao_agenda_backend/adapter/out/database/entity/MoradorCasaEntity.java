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
@Table(name = "Morador_Casa")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MoradorCasaEntity {

    @Id
    @Column(name = "id_paciente")
    private  PacienteEntity idPaciente;
    @Column(name = "nome_morador")
    private String nomeMorador;
    @Column(name = "idade_morador")
    private Integer idadeMorador;
    @Column(name = "parentesco_morador")
    private String parentescoMorador;

}
