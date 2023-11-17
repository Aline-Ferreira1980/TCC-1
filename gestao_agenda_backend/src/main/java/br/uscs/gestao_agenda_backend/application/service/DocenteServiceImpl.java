package br.uscs.gestao_agenda_backend.application.service;

import br.uscs.gestao_agenda_backend.application.common.DocenteMapper;
import br.uscs.gestao_agenda_backend.application.dto.DocenteResponse;
import br.uscs.gestao_agenda_backend.application.port.ConfirmacaoService;
import br.uscs.gestao_agenda_backend.application.port.DocenteService;
import br.uscs.gestao_agenda_backend.domain.model.*;
import br.uscs.gestao_agenda_backend.domain.model.enums.UserRole;
import br.uscs.gestao_agenda_backend.domain.port.DocenteRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DocenteServiceImpl implements DocenteService {
    private final DocenteRepository docenteRepository;
    private final DocenteMapper docenteMapper;
    private final ConfirmacaoService confirmacaoService;


    @Override
    public DocenteResponse cadastraDocente(Docente request) {
        if (docenteRepository.findByEmail(request.getEmail()) != null) {
            throw new EntityExistsException("O email já está em uso.");
        }

        String senhaCriptografada = new BCryptPasswordEncoder().encode(request.getSenha());
        request.setSenha(senhaCriptografada);

        request.setRole(UserRole.DOCENTE);

        request.setConfirmed(false);
        String token = UUID.randomUUID().toString();
        request.setToken(token);
        confirmacaoService.enviarEmailConfirmacao(request.getEmail(), token);


        return docenteMapper.toResponse(docenteRepository.save(request));
    }

    @Override
    public List<DocenteResponse> findAll() {
        return docenteRepository.findAll()
                .stream()
                .map(docenteMapper::toResponse)
                .collect(Collectors.toList());

    }

    @Override
    public Optional<DocenteResponse> findById(Long id) {
        Optional<Docente> rs = docenteRepository.findById(id);
        return rs.map(docenteMapper::toResponse);
    }

    @Override
    public Optional<DocenteResponse> atualizaDocente(Long id, Docente request) {
        Optional<Docente> rs = docenteRepository.findById(id);


        if(rs.isPresent()) {
            Docente docente = rs.get();
            BeanUtils.copyProperties(request, docente,
                    "id",
                    "email",
                    "ruscs",
                    "servicos",
                    "estagiarios");

            return Optional.ofNullable(docenteMapper.toResponse(docenteRepository.save(docente)));
        }
        return Optional.empty();
    }

    @Override
    public void deletaDocente(Long id) {
        docenteRepository.deleteById(id);
        docenteRepository.flush();
    }

    @Override
    public List<DocenteResponse> findByServico(String acronimo) {
        return docenteRepository.findByServicosAcronimo(acronimo)
                .stream()
                .map(docenteMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<DocenteResponse> findServicoEmpty() {
        return docenteRepository.findByServicosEmpty()
                .stream()
                .map(docenteMapper::toResponse)
                .collect(Collectors.toList());
    }


}
