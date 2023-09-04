package br.uscs.gestao_agenda_backend.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Servico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String acronimo;
    private String nome;
    private String descricao;
    @ManyToMany
    @JoinTable(name = "estagiarios_servicos",
            joinColumns = @JoinColumn(name = "servico_fk"),
            inverseJoinColumns = @JoinColumn(name = "estagiario_fk"))
    private Set<Estagiario> estagiarios;

    @ManyToMany
    @JoinTable(name = "docentes_servicos",
            joinColumns = @JoinColumn(name = "servico_fk"),
            inverseJoinColumns = @JoinColumn(name = "docente_fk"))
    private Set<Docente> docentes;
}
