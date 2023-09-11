package br.uscs.gestao_agenda_backend.application.service;

import br.uscs.gestao_agenda_backend.application.common.DocenteMapper;
import br.uscs.gestao_agenda_backend.application.dto.DocenteResponse;
import br.uscs.gestao_agenda_backend.application.dto.SalaResponse;
import br.uscs.gestao_agenda_backend.application.port.DocenteService;
import br.uscs.gestao_agenda_backend.application.request.CadastroDocenteRequest;
import br.uscs.gestao_agenda_backend.domain.model.Docente;
import br.uscs.gestao_agenda_backend.domain.model.Estagiario;
import br.uscs.gestao_agenda_backend.domain.model.HorarioTrabalho;
import br.uscs.gestao_agenda_backend.domain.model.Servico;
import br.uscs.gestao_agenda_backend.domain.port.DocenteRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DocenteServiceImpl implements DocenteService {
    DocenteRepository docenteRepository;
    DocenteMapper docenteMapper;


    @Override
    public DocenteResponse cadastraDocente(Docente request) {
        if (docenteRepository.findByEmail(request.getEmail()) != null) {
            // TODO: Criar uma exeção customizad
            throw new RuntimeException("O email já está em uso.");
        }

        String senhaCriptografada = new BCryptPasswordEncoder().encode(request.getSenha());
        request.setSenha(senhaCriptografada);

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
        return rs.map(docente -> docenteMapper.toResponse(docente));
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


}
