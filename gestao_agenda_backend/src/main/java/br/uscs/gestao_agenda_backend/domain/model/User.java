package br.uscs.gestao_agenda_backend.domain.model;


import br.uscs.gestao_agenda_backend.domain.model.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
//@MappedSuperclass
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    private String nome;
    private String sobrenome;
    private String senha;

    private String token;
    private Boolean confirmed;

}
