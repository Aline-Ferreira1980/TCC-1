package br.uscs.gestao_agenda_backend.adapter.out.database.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "Escolaridade")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EscolaridadeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_escolaridade")
    private Integer idEscolaridade;
    @Column(name = "nome_escolaridade")
    private String nomeEscolaridade;
}
