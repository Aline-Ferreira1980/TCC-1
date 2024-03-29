package br.uscs.gestao_agenda_backend.infrastructure.security.config;

import br.uscs.gestao_agenda_backend.domain.model.User;
import br.uscs.gestao_agenda_backend.domain.port.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;


@Service
public class AppUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;


    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        if(userRepository.findByEmail(email).isPresent()){
            User user = userRepository.findByEmail(email).get();
            if(user.getConfirmed()){
                return new AuthUser(user, getAuthorities(user));
            }
            throw new UsernameNotFoundException("Usuário com e-mail nao confirmado");
        }
        throw new UsernameNotFoundException("Usuário não encontrado com e-mail informado");
    }


    private Collection<GrantedAuthority> getAuthorities(User user){
        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority (user.getRole().getRole()));

        return authorities;
    }
}
