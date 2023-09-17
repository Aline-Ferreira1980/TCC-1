package br.uscs.gestao_agenda_backend.application.common;

import br.uscs.gestao_agenda_backend.application.dto.ServicoPropertyResponse;
import br.uscs.gestao_agenda_backend.application.dto.ServicoResponse;
import br.uscs.gestao_agenda_backend.application.request.ServicoRequest;
import br.uscs.gestao_agenda_backend.domain.model.Servico;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class ServicoMapper {
    private final ModelMapper modelMapper;

    public ServicoResponse toResponse(Servico servico) {
        return modelMapper.map(servico, ServicoResponse.class);
    }

    public ServicoPropertyResponse toPropertyResponse(Servico servico) {
        return modelMapper.map(servico, ServicoPropertyResponse.class);
    }

    public Set<ServicoPropertyResponse> toPropertyResponseSet(Set<Servico> servicos) {
        if (servicos != null) {
            return servicos.stream()
                    .map(this::toPropertyResponse)
                    .collect(Collectors.toSet());
        }
        return null;
    }

    public Servico fromRequest(ServicoRequest request) {
        return modelMapper.map(request, Servico.class);
    }
}
