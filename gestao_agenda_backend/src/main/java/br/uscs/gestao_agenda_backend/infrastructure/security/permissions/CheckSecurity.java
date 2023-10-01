package br.uscs.gestao_agenda_backend.infrastructure.security.permissions;

import org.springframework.security.access.prepost.PreAuthorize;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

public @interface CheckSecurity {
    public @interface User{


        @PreAuthorize("isAuthenticated() and " +
                "hasAnyAuthority('docente', 'estagiario')")
        @Retention(RUNTIME)
        @Target(METHOD)
        public @interface canViewAllPacientesProfiles{
        }

        @PreAuthorize("isAuthenticated() and " +
                "(hasAnyAuthority('docente', 'estagiario') or #id == authentication.principal.id)")
        @Retention(RUNTIME)
        @Target(METHOD)
        public @interface canViewPacienteProfile{
            String id() default "id";
        }

        @PreAuthorize("isAuthenticated() and " +
                "(hasAuthority('docente') or #id == authentication.principal.id)")
        @Retention(RUNTIME)
        @Target(METHOD)
        public @interface canEditProfiles{
            String id() default "id";
        }

        @PreAuthorize("isAuthenticated() and " +
                "(hasAuthority('docente') or #id == authentication.principal.id)")
        @Retention(RUNTIME)
        @Target(METHOD)
        public @interface canDeleteProfile{
            String id() default "id";
        }


    }

}
