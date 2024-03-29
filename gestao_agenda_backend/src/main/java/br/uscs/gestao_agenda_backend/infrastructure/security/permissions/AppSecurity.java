package br.uscs.gestao_agenda_backend.infrastructure.security.permissions;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.common.exceptions.UnauthorizedUserException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AppSecurity {
    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public Long getUserId(){
        Jwt jwt = (Jwt) getAuthentication().getPrincipal();
        return jwt.getClaim("user_id");
    }

    public List<String> getRoles(){
        return getAuthentication().getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
    }

    public String getEmail() {
        Jwt jwt = (Jwt) getAuthentication().getPrincipal();
        return jwt.getClaim("user_name");
    }

    public boolean validateUserAuthority(String expectedAuthority, String email){
        return this.getRoles().contains(expectedAuthority) && !this.getEmail().equals(email);
    }

    public boolean validateUserAuthority(String expectedAuthority, Long userId){
        return this.getRoles().contains(expectedAuthority) && !this.getUserId().equals(userId);
    }

}
