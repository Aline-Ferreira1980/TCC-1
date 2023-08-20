package br.uscs.gestao_agenda_backend.adapter.out.database.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "Sala")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SalaEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_sala")
    private Integer idSala;

    @Column(name = "nome_sala")
    private String nomeSala;

    @Column(name = "local_sala")
    private String localSala;

    @Column(name = "disponibilidade")
    private boolean disponibilidade;

}
