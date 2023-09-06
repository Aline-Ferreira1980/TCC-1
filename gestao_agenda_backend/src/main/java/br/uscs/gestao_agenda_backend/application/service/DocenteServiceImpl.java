package br.uscs.gestao_agenda_backend.application.service;

import br.uscs.gestao_agenda_backend.application.common.DocenteMapper;
import br.uscs.gestao_agenda_backend.application.dto.DocenteResponse;
import br.uscs.gestao_agenda_backend.application.port.DocenteService;
import br.uscs.gestao_agenda_backend.application.request.CadastroDocenteRequest;
import br.uscs.gestao_agenda_backend.domain.model.Docente;
import br.uscs.gestao_agenda_backend.domain.port.DocenteRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

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
}
