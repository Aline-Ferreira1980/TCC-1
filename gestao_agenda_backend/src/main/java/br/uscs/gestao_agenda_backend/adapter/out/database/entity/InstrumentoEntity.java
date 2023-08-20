package br.uscs.gestao_agenda_backend.adapter.out.database.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "Instrumento")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InstrumentoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_teste")
    private Integer idTeste;
    @Column(name = "nome_teste")
    private String nomeTeste;

}
