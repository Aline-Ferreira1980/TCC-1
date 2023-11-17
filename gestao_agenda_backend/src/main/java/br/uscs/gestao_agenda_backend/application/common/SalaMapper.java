package br.uscs.gestao_agenda_backend.application.common;

import br.uscs.gestao_agenda_backend.application.dto.SalaPropertyResponse;
import br.uscs.gestao_agenda_backend.application.dto.SalaResponse;
import br.uscs.gestao_agenda_backend.application.request.AtualizaSalaRequest;
import br.uscs.gestao_agenda_backend.application.request.SalaRequest;
import br.uscs.gestao_agenda_backend.domain.model.Sala;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class SalaMapper {

    private final ModelMapper modelMapper;

    public Sala fromRequest(SalaRequest request) {
        return modelMapper.map(request, Sala.class);
    }

    public Sala fromRequest(AtualizaSalaRequest request) {
        return modelMapper.map(request, Sala.class);
    }

    public SalaResponse toResponse(Sala sala) {
        return modelMapper.map(sala, SalaResponse.class);
    }

    public SalaPropertyResponse toPropertyResponse(Sala sala) {
        return modelMapper.map(sala, SalaPropertyResponse.class);
    }
}
