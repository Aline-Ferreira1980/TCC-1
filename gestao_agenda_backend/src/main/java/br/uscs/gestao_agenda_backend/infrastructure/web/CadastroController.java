package br.uscs.gestao_agenda_backend.infrastructure.web;

import br.uscs.gestao_agenda_backend.application.common.EstagiarioMapper;
import br.uscs.gestao_agenda_backend.application.common.PacienteMapper;
import br.uscs.gestao_agenda_backend.application.request.CadastroPacienteRequest;
import br.uscs.gestao_agenda_backend.application.request.CadastroEstagiarioRequest;
import br.uscs.gestao_agenda_backend.application.port.PacienteService;
import br.uscs.gestao_agenda_backend.application.port.EstagiarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("cadastro")
public class CadastroController {

    @Autowired
    private EstagiarioService estagiarioService;

    @Autowired
    private PacienteService pacienteService;

    @Autowired
    private PacienteMapper pacienteMapper;




}
