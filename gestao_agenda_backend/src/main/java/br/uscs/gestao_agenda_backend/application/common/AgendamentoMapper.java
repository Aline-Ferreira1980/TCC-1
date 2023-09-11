package br.uscs.gestao_agenda_backend.application.common;

import br.uscs.gestao_agenda_backend.application.dto.*;
import br.uscs.gestao_agenda_backend.domain.model.Agendamento;
import br.uscs.gestao_agenda_backend.domain.model.Servico;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class AgendamentoMapper {
    private final ModelMapper modelMapper;
    private final EstagiarioMapper estagiarioMapper;
    private final PacienteMapper pacienteMapper;
    private final SalaMapper salaMapper;

    public AgendamentoResponse toResponse(Agendamento agendamento){
        return modelMapper.map(agendamento, AgendamentoResponse.class);
    }

    public AgendamentoPropertyResponse toPropertyResponse(Agendamento agendamento){
        return AgendamentoPropertyResponse.builder()
                .id(agendamento.getId())
                .estagiario(estagiarioMapper.toPropertyResponse(agendamento.getEstagiario()))
                .paciente(pacienteMapper.toPropertyResponse(agendamento.getPaciente()))
                .sala(salaMapper.toPropertyResponse(agendamento.getSala()))
                .inicioAgendamento(agendamento.getInicioAgendamento())
                .fimAgendamento(agendamento.getFimAgendamento())
                .build();
    }

    public List<AgendamentoPropertyResponse> toPropertyResponseList(List<Agendamento> agendamentos) {
        if (agendamentos != null) {
            return agendamentos.stream()
                    .map(this::toPropertyResponse)
                    .collect(Collectors.toList());
        }
        return null;
    }
}
