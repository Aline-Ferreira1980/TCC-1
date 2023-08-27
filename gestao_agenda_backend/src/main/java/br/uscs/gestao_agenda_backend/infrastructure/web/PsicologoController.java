package br.uscs.gestao_agenda_backend.infrastructure.web;

import br.uscs.gestao_agenda_backend.application.dto.in.CadastroHorarioTrabalhoRequest;
import br.uscs.gestao_agenda_backend.application.dto.in.UpdatePsicologoRequest;
import br.uscs.gestao_agenda_backend.application.dto.out.PsicologoResponse;
import br.uscs.gestao_agenda_backend.application.port.PsicologoService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("psicologo")
public class PsicologoController {

    @Autowired
    private PsicologoService psicologoService;

    @GetMapping("/disponibilidade")
    public ResponseEntity<List<PsicologoResponse>> getAvailabilityByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<PsicologoResponse> response = psicologoService.getAllAvailableInDataRange(startDate, endDate);
        return new ResponseEntity<List<PsicologoResponse>>(response, HttpStatus.OK);
    }

    @GetMapping("/listar")
    public ResponseEntity<List<PsicologoResponse>> getAllPsicologos() {
        List<PsicologoResponse> response = psicologoService.findAll();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/atualizar")
    public ResponseEntity<PsicologoResponse> setHorarioTrabalho(
            @RequestBody UpdatePsicologoRequest request){

        PsicologoResponse response = psicologoService.uodatePsicologo(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
