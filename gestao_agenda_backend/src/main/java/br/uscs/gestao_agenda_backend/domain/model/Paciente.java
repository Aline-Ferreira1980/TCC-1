package br.uscs.gestao_agenda_backend.domain.model;


import br.uscs.gestao_agenda_backend.domain.model.enums.EstadoCivil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Paciente extends User {

    private String nomeSocial;

    private LocalDate dataNascimento;

    private String genero;

    @Enumerated(EnumType.STRING)
    private EstadoCivil estadoCivil;

    private String etniaRacial;

    @Embedded
    private Endereco endereco;

    //    @OneToMany(mappedBy = "paciente", cascade = CascadeType.ALL, orphanRemoval = true)
    @ElementCollection
    private Set<Telefone> telefone;

    private Boolean isMenorIdade;
    private String relacaoRepresentante;
    private String representanteNome;

    @ManyToOne
    private Estagiario estagiario;

    @OneToMany(mappedBy = "paciente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Agendamento> agendamentos;


}
