package br.uscs.gestao_agenda_backend.infrastructure.security.permissions;

import org.springframework.security.access.prepost.PreAuthorize;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

public @interface CheckSecurity {

    @interface Agendamento {

        @PreAuthorize("isAuthenticated() and " +
                "hasAnyAuthority('docente', 'estagiario', 'paciente')")
        @Retention(RUNTIME)
        @Target(METHOD)
        @interface CanManageAgendamento {
        }

        @PreAuthorize("isAuthenticated() and " +
                "hasAnyAuthority('docente', 'estagiario')")
        @Retention(RUNTIME)
        @Target(METHOD)
        @interface CanViewAgendamentoAdmin {
        }

    }

    @interface Docente{
        @PreAuthorize("isAuthenticated() and " +
                "hasAnyAuthority('docente')")
        @Retention(RUNTIME)
        @Target(METHOD)
        @interface CanManageDocente {
        }

        @PreAuthorize("isAuthenticated() and " +
                "hasAnyAuthority('docente', 'estagiario')")
        @Retention(RUNTIME)
        @Target(METHOD)
        @interface CanViewDocente {
        }


    }

    @interface Estagiario{
        @PreAuthorize("isAuthenticated() and " +
                "hasAnyAuthority('docente', 'estagiario')")
        @Retention(RUNTIME)
        @Target(METHOD)
        @interface CanManageEstagiario {
        }
    }

    @interface Paciente{
        @PreAuthorize("isAuthenticated() and " +
                "hasAnyAuthority('docente', 'estagiario', 'paciente')")
        @Retention(RUNTIME)
        @Target(METHOD)
        @interface CanManagePaciente {
        }

        @PreAuthorize("isAuthenticated() and " +
                "hasAnyAuthority('docente', 'estagiario')")
        @Retention(RUNTIME)
        @Target(METHOD)
        @interface CanViewPacienteAdmin{
        }

    }

    @interface Sala{
        @PreAuthorize("isAuthenticated() and " +
                "hasAnyAuthority('docente', 'estagiario')")
        @Retention(RUNTIME)
        @Target(METHOD)
        @interface CanManageSala {}
    }

    @interface Servico{
        @PreAuthorize("isAuthenticated() and " +
                "hasAnyAuthority('docente')")
        @Retention(RUNTIME)
        @Target(METHOD)
        @interface CanCreateDeleteServico {}

        @PreAuthorize("isAuthenticated() and " +
                "hasAnyAuthority('docente', 'estagiario')")
        @Retention(RUNTIME)
        @Target(METHOD)
        @interface CanViewServico {}

        @PreAuthorize("isAuthenticated() and " +
                "hasAnyAuthority('docente', 'estagiario')")
        @Retention(RUNTIME)
        @Target(METHOD)
        @interface CanAddRemoveServicoToUser {}

        @PreAuthorize("isAuthenticated() and " +
                "hasAnyAuthority('docente')")
        @Retention(RUNTIME)
        @Target(METHOD)
        @interface CanAddRemoveServicoToUserAdmin {}


    }


}
