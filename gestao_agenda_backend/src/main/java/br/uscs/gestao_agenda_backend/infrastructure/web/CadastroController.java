package br.uscs.gestao_agenda_backend.infrastructure.web;

import br.uscs.gestao_agenda_backend.application.common.PacienteMapper;
import br.uscs.gestao_agenda_backend.application.dto.in.CadastroPacienteRequest;
import br.uscs.gestao_agenda_backend.application.dto.in.CadastroPsicologoRequest;
import br.uscs.gestao_agenda_backend.application.port.PacienteService;
import br.uscs.gestao_agenda_backend.application.port.PsicologoService;
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
    private PsicologoService psicologoService;

    @Autowired
    private PacienteService pacienteService;

    @PostMapping("/psicologos")
    public ResponseEntity<String> cadastrarPsicologo(@RequestBody CadastroPsicologoRequest request) {
        psicologoService.cadastrarPsicologo(request);
        return new ResponseEntity<>("Psicólogo cadastrado com sucesso.", HttpStatus.CREATED);
    }

    @PostMapping("/pacientes")
    public ResponseEntity<String> cadastrarPaciente(@RequestBody CadastroPacienteRequest request) {
        pacienteService.cadastraPaciente(PacienteMapper.fromRequest(request));
        return new ResponseEntity<>("Paciente cadastrado com sucesso.", HttpStatus.CREATED);
    }

}
