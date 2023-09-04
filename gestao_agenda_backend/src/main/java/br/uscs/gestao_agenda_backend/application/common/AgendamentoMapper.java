package br.uscs.gestao_agenda_backend.application.common;

import br.uscs.gestao_agenda_backend.application.dto.AgendamentoResponse;
import br.uscs.gestao_agenda_backend.application.dto.ServicoPropertyResponse;
import br.uscs.gestao_agenda_backend.application.dto.ServicoResponse;
import br.uscs.gestao_agenda_backend.domain.model.Agendamento;
import br.uscs.gestao_agenda_backend.domain.model.Servico;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class AgendamentoMapper {
    private final ModelMapper modelMapper;
    public AgendamentoResponse toResponse(Agendamento agendamento){
        return modelMapper.map(agendamento, AgendamentoResponse.class);
    }

}
