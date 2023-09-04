package br.uscs.gestao_agenda_backend.application.service;

import br.uscs.gestao_agenda_backend.application.common.SalaMapper;
import br.uscs.gestao_agenda_backend.application.request.SalaRequest;
import br.uscs.gestao_agenda_backend.application.dto.SalaResponse;
import br.uscs.gestao_agenda_backend.application.port.SalaService;
import br.uscs.gestao_agenda_backend.domain.model.Sala;
import br.uscs.gestao_agenda_backend.domain.port.SalaRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@AllArgsConstructor
public class SalaServiceImp implements SalaService {

    private final SalaRepository salaRepository;
    private final SalaMapper salaMapper;

    @Override
    public void cadastraSala(SalaRequest request) {
        Sala sala = Sala.builder()
                .nome(request.getNome())
                .local(request.getLocal()).build();

        salaRepository.save(sala);
    }

    @Override
    public List<SalaResponse> findAll() {
        return salaRepository.findAll().stream().map(salaMapper::toResponse).toList();
    }
}
