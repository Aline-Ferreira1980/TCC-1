package br.uscs.gestao_agenda_backend.adapter.out.database.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "Funcionario")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FuncionarioEntity implements Serializable {

    @Id
    @Column(name = "ruscs_funcionario")
    private Integer ruscsFuncionario;
    @Column(name = "nome")
    private String nome;
    @Column(name = "funcao")
    private String funcao;
    @Column(name = "id_instrumento")
    private InstrumentoEntity idInstrumento;

}
