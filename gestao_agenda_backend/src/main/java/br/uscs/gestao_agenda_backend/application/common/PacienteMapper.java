package br.uscs.gestao_agenda_backend.application.common;

import br.uscs.gestao_agenda_backend.application.dto.PacientePropertyResponse;
import br.uscs.gestao_agenda_backend.application.dto.PacienteResponse;
import br.uscs.gestao_agenda_backend.application.request.AtualizaPacienteRequest;
import br.uscs.gestao_agenda_backend.application.request.CadastroPacienteRequest;
import br.uscs.gestao_agenda_backend.domain.model.Paciente;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class PacienteMapper {

    private final ModelMapper modelMapper;
    private final EstagiarioMapper estagiarioMapper;


    public Paciente fromRequest(CadastroPacienteRequest request) {
        return modelMapper.map(request, Paciente.class);
    }

    public Paciente fromRequest(AtualizaPacienteRequest request) {
        return modelMapper.map(request, Paciente.class);
    }

    public PacienteResponse toResponse(Paciente paciente) {
        AgendamentoMapper agendamentoMapper = new AgendamentoMapper(
                new ModelMapper(), this.estagiarioMapper, this, new SalaMapper(this.modelMapper)
        );
        PacienteResponse response = PacienteResponse.builder()
                .estagiario(estagiarioMapper.toPropertyResponse(paciente.getEstagiario()))
                .build();

        BeanUtils.copyProperties(paciente, response, "agendamentos", "estagiario");
        return response;
//        return modelMapper.map(paciente, PacienteResponse.class);
    }

    public PacientePropertyResponse toPropertyResponse(Paciente paciente) {
        return modelMapper.map(paciente, PacientePropertyResponse.class);
    }

    public Set<PacientePropertyResponse> toPropertyResponseSet(Set<Paciente> pacientes) {
        if (pacientes != null) {
            return pacientes.stream()
                    .map(this::toPropertyResponse)
                    .collect(Collectors.toSet());
        }
        return null;
    }
}
