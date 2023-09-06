package br.uscs.gestao_agenda_backend.application.service;

import br.uscs.gestao_agenda_backend.application.common.SalaMapper;
import br.uscs.gestao_agenda_backend.application.dto.SalaResponse;
import br.uscs.gestao_agenda_backend.application.port.SalaService;
import br.uscs.gestao_agenda_backend.domain.model.Sala;
import br.uscs.gestao_agenda_backend.domain.port.SalaRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SalaServiceImp implements SalaService {

    private final SalaRepository salaRepository;
    private final SalaMapper salaMapper;

    @Override
    public SalaResponse cadastraSala(Sala request) {
        return salaMapper.toResponse(salaRepository.save(request));
    }

    @Override
    public List<SalaResponse> findAll() {
        return salaRepository.findAll().stream().map(salaMapper::toResponse).toList();
    }

    @Override
    public Optional<SalaResponse> findById(Long id) {
        Optional<Sala> sala = salaRepository.findById(id);
        return sala.map(salaMapper::toResponse);
    }

    @Override
    public Optional<SalaResponse> atualizaSala(Long id, Sala request) {
        Optional<Sala> rs = salaRepository.findById(id);

        if (rs.isPresent()){
            Sala sala = rs.get();
            BeanUtils.copyProperties(request, sala, "id", "agendamentos", "local");
            return Optional.ofNullable(salaMapper.toResponse(salaRepository.save(sala)));
        }
        return Optional.empty();
    }

    @Override
    public void deletaSala(Long id) {
        // TODO: Mandar erro customizado
        salaRepository.deleteById(id);
        salaRepository.flush();
    }


}
