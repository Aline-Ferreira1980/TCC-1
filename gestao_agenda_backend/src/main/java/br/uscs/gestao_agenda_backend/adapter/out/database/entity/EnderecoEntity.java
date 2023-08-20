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
@Table(name = "Endereco")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnderecoEntity implements Serializable
{

    @Id
    @Column(name = "id_paciente")
    private PacienteEntity idPaciente;
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
