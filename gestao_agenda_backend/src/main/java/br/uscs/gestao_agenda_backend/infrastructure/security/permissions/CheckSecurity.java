package br.uscs.gestao_agenda_backend.infrastructure.security.permissions;

import org.springframework.security.access.prepost.PreAuthorize;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

public @interface CheckSecurity {
    @interface User{


        @PreAuthorize("isAuthenticated() and " +
                "hasAnyAuthority('docente', 'estagiario')")
        @Retention(RUNTIME)
        @Target(METHOD)
        @interface CanViewAllPacientesProfiles{
        }

        @PreAuthorize("isAuthenticated() and " +
                "(hasAnyAuthority('docente', 'estagiario') or #id == authentication.principal.id)")
        @Retention(RUNTIME)
        @Target(METHOD)
        @interface CanViewPacienteProfile{
            String id() default "id";
        }

        @PreAuthorize("isAuthenticated() and " +
                "(hasAuthority('docente') or #id == authentication.principal.id)")
        @Retention(RUNTIME)
        @Target(METHOD)
        @interface CanEditProfiles{
            String id() default "id";
        }

        @PreAuthorize("isAuthenticated() and " +
                "(hasAuthority('docente') or #id == authentication.principal.id)")
        @Retention(RUNTIME)
        @Target(METHOD)
        @interface CanDeleteProfile{
            String id() default "id";
        }

        @PreAuthorize("isAuthenticated() and " +
                "hasAuthority('DOCENTE')")
        @Retention(RUNTIME)
        @Target(METHOD)
        @interface CanCreateAndDeleteDocente{
        }

        @PreAuthorize("isAuthenticated() and " +
                "(hasAnyAuthority('docente', 'estagiario') or #id == authentication.principal.id)")
        @Retention(RUNTIME)
        @Target(METHOD)
        @interface CanViewListAgendamento{
            String id() default "id";
        }


    }

}
