package br.uscs.gestao_agenda_backend.application.port;

import br.uscs.gestao_agenda_backend.application.dto.in.SalaRequest;
import br.uscs.gestao_agenda_backend.application.dto.out.SalaResponse;

import java.util.List;

public interface SalaService {

    void cadastraSala(SalaRequest request);

    List<SalaResponse> findAll();
}
