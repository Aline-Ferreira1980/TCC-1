package br.uscs.gestao_agenda_backend.adapter.out.database.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "Servico")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServicoEntity implements Serializable
{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_servico")
    private Integer idServico;

    @Column(name = "nome_servico")
    private String nomeServico;

    @Column(name = "acronimo_servico")
    private String acronimoServico;

}
