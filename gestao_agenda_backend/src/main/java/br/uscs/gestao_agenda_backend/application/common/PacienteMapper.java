package br.uscs.gestao_agenda_backend.application.common;

import br.uscs.gestao_agenda_backend.application.dto.PacientePropertyResponse;
import br.uscs.gestao_agenda_backend.application.request.AtualizaPacienteRequest;
import br.uscs.gestao_agenda_backend.application.request.CadastroPacienteRequest;
import br.uscs.gestao_agenda_backend.application.dto.PacienteResponse;
import br.uscs.gestao_agenda_backend.domain.model.Paciente;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class PacienteMapper {

//    static public Paciente fromRequest(CadastroPacienteRequest request){
//        return Paciente.builder()
//                .nome(request.getNome())
//                .email(request.getEmail())
//                .senha(request.getSenha())
//                .queixa(request.getQueixa())
//                .role(UserRole.PACIENTE)
//                .build();
//    }
//
//    static public PacienteResponse toResponse(Paciente paciente){
//        return PacienteResponse.builder()
//                .nome(paciente.getNome())
//                .email(paciente.getEmail())
//                .queixa(paciente.getQueixa())
//                .build();
//    }

    private final ModelMapper modelMapper;

    public Paciente fromRequest(CadastroPacienteRequest request){
        return modelMapper.map(request, Paciente.class);
    }
    public Paciente fromRequest(AtualizaPacienteRequest request){
        return modelMapper.map(request, Paciente.class);
    }

    public PacienteResponse toResponse(Paciente paciente){
        return modelMapper.map(paciente, PacienteResponse.class);
    }

    public PacientePropertyResponse toPropertyResponse(Paciente paciente){
        return modelMapper.map(paciente, PacientePropertyResponse.class);
    }
}
