package br.uscs.gestao_agenda_backend.infrastructure.security;

import br.uscs.gestao_agenda_backend.domain.model.Docente;
import br.uscs.gestao_agenda_backend.domain.model.Estagiario;
import br.uscs.gestao_agenda_backend.domain.model.Paciente;
import br.uscs.gestao_agenda_backend.domain.model.User;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;


import java.util.Collection;
@Getter
public class AuthUser extends org.springframework.security.core.userdetails.User {
    private final Long id;
    private final String nome;

    public AuthUser(Paciente usuario, Collection<? extends GrantedAuthority> authorities) {
        super(usuario.getEmail(), usuario.getSenha(), authorities);
        this.id = usuario.getId();
        this.nome = usuario.getNome();

    }

    public AuthUser(Estagiario usuario, Collection<? extends GrantedAuthority> authorities) {
        super(usuario.getEmail(), usuario.getSenha(), authorities);
        this.id = usuario.getId();
        this.nome = usuario.getNome();
    }

    public AuthUser(Docente usuario, Collection<? extends GrantedAuthority> authorities) {
        super(usuario.getEmail(), usuario.getSenha(), authorities);
        this.id = usuario.getId();
        this.nome = usuario.getNome();
    }
}
