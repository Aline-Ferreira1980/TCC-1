package br.uscs.gestao_agenda_backend.application.common;

import br.uscs.gestao_agenda_backend.application.dto.in.SalaRequest;
import br.uscs.gestao_agenda_backend.application.dto.out.SalaResponse;
import br.uscs.gestao_agenda_backend.domain.model.Sala;

public class SalaMapper {

    static public Sala fromRequest(SalaRequest request){
        return Sala.builder()
                .nome(request.getNome())
                .local(request.getLocal())
                .build();
    }

    static public SalaResponse toResponse(Sala sala){
        return SalaResponse.builder()
                .nome(sala.getNome())
                .local(sala.getLocal())
                .build();
    }
}
