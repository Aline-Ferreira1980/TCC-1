package br.uscs.gestao_agenda_backend.application.service;

import br.uscs.gestao_agenda_backend.application.common.AgendamentoMapper;
import br.uscs.gestao_agenda_backend.application.common.ServicoMapper;
import br.uscs.gestao_agenda_backend.application.dto.DocenteResponse;
import br.uscs.gestao_agenda_backend.application.dto.ServicoResponse;
import br.uscs.gestao_agenda_backend.application.port.ServicoService;
import br.uscs.gestao_agenda_backend.application.request.ServicoRequest;
import br.uscs.gestao_agenda_backend.domain.model.Docente;
import br.uscs.gestao_agenda_backend.domain.model.Estagiario;
import br.uscs.gestao_agenda_backend.domain.model.HorarioTrabalho;
import br.uscs.gestao_agenda_backend.domain.model.Servico;
import br.uscs.gestao_agenda_backend.domain.port.ServicoRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ServicoServiceImpl implements ServicoService {

    private final ServicoRepository servicoRepository;
    private final ServicoMapper servicoMapper;
    @Transactional
    @Override
    public Optional<ServicoResponse> cadastrarServico(ServicoRequest request) {
        Optional<Servico> rs = servicoRepository.findByAcronimo(request.getAcronimo());

        if(rs.isPresent()) {
            // TODO: Criar uma exeção customizad
            throw new IllegalArgumentException("Serviço com este acrônimo ja existe");
        }
        Servico servico = servicoRepository.save(servicoMapper.fromRequest(request));
        return Optional.ofNullable(servicoMapper.toResponse(servico));
    }

    @Override
    public Optional<ServicoResponse> findById(Long id) {
        Optional<Servico> rs = servicoRepository.findById(id);
        return rs.map(servicoMapper::toResponse);
    }

    public List<ServicoResponse> findAll() {
        return servicoRepository.findAll()
                .stream()
                .map(servicoMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<ServicoResponse> udpateServico(Long id, ServicoRequest request) {
        Optional<Servico> rs = servicoRepository.findById(id);

        if(rs.isPresent()) {
            Servico servico = rs.get();
            BeanUtils.copyProperties(request, servico,
                    "id",
                    "estagiarios",
                    "docentes");
            return Optional.ofNullable(servicoMapper.toResponse(servicoRepository.save(servico)));
        }
        return Optional.empty();
    }

    @Override
    public void deletaServico(Long id) {
        servicoRepository.deleteById(id);
        servicoRepository.flush();
        // TODO: Mandar erro customizado
    }


}
