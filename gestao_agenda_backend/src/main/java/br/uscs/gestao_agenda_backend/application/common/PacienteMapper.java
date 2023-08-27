package br.uscs.gestao_agenda_backend.application.common;

import br.uscs.gestao_agenda_backend.application.dto.in.CadastroPacienteRequest;
import br.uscs.gestao_agenda_backend.application.dto.out.PacienteResponse;
import br.uscs.gestao_agenda_backend.domain.model.Paciente;
import br.uscs.gestao_agenda_backend.domain.model.UserRole;

public class PacienteMapper {

    static public Paciente fromRequest(CadastroPacienteRequest request){
        return Paciente.builder()
                .nome(request.getNome())
                .email(request.getEmail())
                .senha(request.getSenha())
                .queixa(request.getQueixa())
                .role(UserRole.PACIENTE)
                .build();
    }

    static public PacienteResponse toResponse(Paciente paciente){
        return PacienteResponse.builder()
                .nome(paciente.getNome())
                .email(paciente.getEmail())
                .queixa(paciente.getQueixa())
                .build();
    }
}
