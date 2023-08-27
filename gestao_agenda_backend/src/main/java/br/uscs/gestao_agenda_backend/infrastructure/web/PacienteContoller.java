package br.uscs.gestao_agenda_backend.infrastructure.web;

import br.uscs.gestao_agenda_backend.application.dto.out.PacienteResponse;
import br.uscs.gestao_agenda_backend.application.port.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("paciente")
public class PacienteContoller {

    @Autowired
    private PacienteService pacienteService;

    @GetMapping("/listar")
    public ResponseEntity<List<PacienteResponse>> getAllPacientes() {
        List<PacienteResponse> response = pacienteService.findAll();
        return new ResponseEntity<List<PacienteResponse>>(response, HttpStatus.OK);
    }

}
