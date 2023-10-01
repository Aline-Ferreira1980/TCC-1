package br.uscs.gestao_agenda_backend.application.service;

import br.uscs.gestao_agenda_backend.application.common.ServicoMapper;
import br.uscs.gestao_agenda_backend.application.dto.ServicoResponse;
import br.uscs.gestao_agenda_backend.application.port.ServicoService;
import br.uscs.gestao_agenda_backend.application.request.ServicoRequest;
import br.uscs.gestao_agenda_backend.domain.model.*;
import br.uscs.gestao_agenda_backend.domain.port.DocenteRepository;
import br.uscs.gestao_agenda_backend.domain.port.EstagiarioRepository;
import br.uscs.gestao_agenda_backend.domain.port.ServicoRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ServicoServiceImpl implements ServicoService {

    private final ServicoRepository servicoRepository;
    private final EstagiarioRepository estagiarioRepository;
    private final DocenteRepository docenteRepository;

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

    @Override
    public Optional<ServicoResponse> addEstagiarioToServico(Long servicoId, Long estagiarioId) {
        Optional<Servico> svc = servicoRepository.findById(servicoId);
        if(svc.isPresent()){
            for(Estagiario e : svc.get().getEstagiarios()){
                if(e.getId().equals(estagiarioId)){
                    throw new IllegalArgumentException("O Estagiario ja foi vinculado a este Serviço");
                }
            }

            Servico servico = svc.get();
            Optional<Estagiario> estag = estagiarioRepository.findById(estagiarioId);
            if (estag.isPresent()){
                servico.getEstagiarios().add(estag.get());
                return Optional.ofNullable(servicoMapper.toResponse(servicoRepository.save(servico)));
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<ServicoResponse> removeEstagiarioFromServico(Long idServico, Long idEstagiario) {
        Optional<Estagiario> rs_estag = estagiarioRepository.findById(idEstagiario);
        Optional<Servico> rs_svc = servicoRepository.findById(idServico);
        if(rs_estag.isPresent() && rs_svc.isPresent()){
            Estagiario estagiario = rs_estag.get();
            Servico servico = rs_svc.get();
            servico.getEstagiarios().remove(estagiario);

            return Optional.ofNullable(servicoMapper.toResponse(servicoRepository.save(servico)));
        }
        return Optional.empty();
    }

    @Override
    public Optional<ServicoResponse> addDocenteToServico(Long idServico, Long idDocente) {
        Optional<Servico> svc = servicoRepository.findById(idServico);
        if(svc.isPresent()){
            for(Estagiario e : svc.get().getEstagiarios()){
                if(e.getId().equals(idDocente)){
                    throw new IllegalArgumentException("O Docente ja foi vinculado a este Serviço");
                }
            }

            Servico servico = svc.get();
            Optional<Docente> doc = docenteRepository.findById(idDocente);
            if (doc.isPresent()){
                servico.getDocentes().add(doc.get());
                return Optional.ofNullable(servicoMapper.toResponse(servicoRepository.save(servico)));
            }
        }

       // TODO retornar um erro de docente ou estagiario nao encontrados
        return Optional.empty();

    }

    @Override
    public Optional<ServicoResponse> removeDocenteFromServico(Long idServico, Long idDocente) {
        Optional<Docente> rs_docente = docenteRepository.findById(idDocente);
        Optional<Servico> rs_svc = servicoRepository.findById(idServico);
        if(rs_docente.isPresent() && rs_svc.isPresent()){
            Docente docente = rs_docente.get();
            Servico servico = rs_svc.get();
            servico.getDocentes().remove(docente);

            return Optional.ofNullable(servicoMapper.toResponse(servicoRepository.save(servico)));
        }
        return Optional.empty();
    }

    @Override
    public Optional<ServicoResponse> addEstagiariosToServico(Long idServico, List<Long> idEstagiarios) {

        // TODO implementar essa verificação erros em outros metodos
        Servico servico = servicoRepository.findById(idServico)
                .orElseThrow(() -> new EntityNotFoundException("Serviço não encontrado"));

        // TODO implementar essa verificação erros em outros metodos
        List<Estagiario> estagiarios = idEstagiarios.stream()
                .map(estagiarioId -> estagiarioRepository.findById(estagiarioId)
                        .orElseThrow(() -> new EntityNotFoundException("Estagiario não encontrado com ID: " + estagiarioId)))
                .toList();

        servico.getEstagiarios().addAll(estagiarios);

        return Optional.ofNullable(servicoMapper.toResponse(servicoRepository.save(servico)));
    }

    @Override
    public Optional<ServicoResponse> addDocentesToServico(Long idServico, List<Long> idDocentes) {
        Servico servico = servicoRepository.findById(idServico)
                .orElseThrow(() -> new EntityNotFoundException("Serviço não encontrado"));

        List<Docente> docentes = idDocentes.stream()
                .map(docenteId -> docenteRepository.findById(docenteId)
                        .orElseThrow(() -> new EntityNotFoundException("Estagiario não encontrado com ID: " + docenteId)))
                .toList();

        servico.getDocentes().addAll(docentes);

        return Optional.ofNullable(servicoMapper.toResponse(servicoRepository.save(servico)));
    }


}
