package br.uscs.gestao_agenda_backend.adapter.out.database.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "Endereco")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnderecoEntity implements Serializable {

    @OneToOne
    @JoinColumn(name = "id_endereco")
    private PacienteEntity idPaciente;

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "id_endereco")
    private Integer endereco;

    @Column(name = "rua_numero")
    private String ruaNumero;

    @Column(name = "bairro")
    private String bairro;

    @Column(name = "cidade")
    private String cidade;

    @Column(name = "estado")
    private String estado;

    @Column(name = "cep")
    private String cep;

}
