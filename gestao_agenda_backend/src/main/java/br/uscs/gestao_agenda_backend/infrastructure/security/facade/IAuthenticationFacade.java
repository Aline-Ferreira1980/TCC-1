package br.uscs.gestao_agenda_backend.infrastructure.security.facade;

import org.springframework.security.core.Authentication;

public interface IAuthenticationFacade {
    Authentication getAuthentication();
}
