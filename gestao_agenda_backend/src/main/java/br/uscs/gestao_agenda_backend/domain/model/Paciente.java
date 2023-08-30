package br.uscs.gestao_agenda_backend.domain.model;


import br.uscs.gestao_agenda_backend.application.dto.in.Endereco;
import br.uscs.gestao_agenda_backend.application.dto.in.Telefone;
import lombok.*;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.Date;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Paciente extends User {

    private String nomeRegistro;
    private Endereco endereco;
    @OneToMany(mappedBy = "paciente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Telefone> telefone;
    private boolean portadorDeficiencia;

    @Builder
    public Paciente(String nomeSocial, String email, Date dataNascimento, String genero, String estadoCivil,
                    String etniaRacial, String senha, String nomeRegistro, Endereco endereco,
                    List<Telefone> telefone, boolean portadorDeficiencia, UserRole role) {
        super(nomeSocial, email, dataNascimento, genero, estadoCivil, etniaRacial, senha, role);

        this.nomeRegistro = nomeRegistro;
        this.endereco = endereco;
        this.telefone = telefone;
        this.portadorDeficiencia = portadorDeficiencia;

    }

}
