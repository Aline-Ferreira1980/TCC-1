package br.uscs.gestao_agenda_backend.application.common;

import br.uscs.gestao_agenda_backend.application.dto.ServicoPropertyResponse;
import br.uscs.gestao_agenda_backend.application.dto.ServicoResponse;
import br.uscs.gestao_agenda_backend.domain.model.Servico;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ServicoMapper {
    private final ModelMapper modelMapper;

    public ServicoResponse toResponse(Servico servico){
        return modelMapper.map(servico, ServicoResponse.class);
    }

    public ServicoPropertyResponse toPropertyResponse(Servico servico){
        return modelMapper.map(servico, ServicoPropertyResponse.class);
    }
}
