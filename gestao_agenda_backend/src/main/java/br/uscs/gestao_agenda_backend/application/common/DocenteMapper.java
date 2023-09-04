package br.uscs.gestao_agenda_backend.application.common;

import br.uscs.gestao_agenda_backend.application.dto.DocenteResponse;
import br.uscs.gestao_agenda_backend.application.dto.DocentePropertyResponse;
import br.uscs.gestao_agenda_backend.application.request.DocenteRequest;
import br.uscs.gestao_agenda_backend.domain.model.Docente;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;


@Component
@AllArgsConstructor
public class DocenteMapper {

    private ModelMapper modelMapper;

    public Docente fromRequest(DocenteRequest request){
        return modelMapper.map(request, Docente.class);
    }

    public DocenteResponse toResponse(Docente docente){
        return modelMapper.map(docente, DocenteResponse.class);
    }

    public DocentePropertyResponse toPropertyResponse(Docente docente){
        return modelMapper.map(docente, DocentePropertyResponse.class);
    }
}
