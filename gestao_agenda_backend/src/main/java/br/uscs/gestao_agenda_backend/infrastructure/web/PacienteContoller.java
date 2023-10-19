package br.uscs.gestao_agenda_backend.infrastructure.web;

import br.uscs.gestao_agenda_backend.application.common.PacienteMapper;
import br.uscs.gestao_agenda_backend.application.dto.PacienteResponse;
import br.uscs.gestao_agenda_backend.application.port.PacienteService;
import br.uscs.gestao_agenda_backend.application.request.AtualizaPacienteRequest;
import br.uscs.gestao_agenda_backend.application.request.CadastroPacienteRequest;
import br.uscs.gestao_agenda_backend.infrastructure.security.permissions.CheckSecurity;
import br.uscs.gestao_agenda_backend.infrastructure.web.openapi.PacienteContollerOpenApi;
import lombok.AllArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@AllArgsConstructor
@RequestMapping(value = "paciente", produces = {"application/json"})
public class PacienteContoller implements PacienteContollerOpenApi {

    private PacienteService pacienteService;
    private final PacienteMapper pacienteMapper;

    @Override
    @PreAuthorize("isAuthenticated() or isAnonymous()")
    @PostMapping(value = "/cadastrar", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PacienteResponse> cadastrarPaciente(CadastroPacienteRequest request,
                                                              UriComponentsBuilder uriBuilder) {
        PacienteResponse response = pacienteService.cadastraPaciente(pacienteMapper.fromRequest(request));
        URI uri = uriBuilder.path("/paciente/{id}").buildAndExpand(response.getId()).toUri();
        return ResponseEntity.created(uri).body(response);
    }

    @Override
    @CheckSecurity.Paciente.CanViewPacienteAdmin
    @GetMapping("/listar")
    public ResponseEntity<List<PacienteResponse>> getAllPacientes() {
        List<PacienteResponse> response = pacienteService.findAll();
        return ResponseEntity.ok(response);
    }


    @Override
    @CheckSecurity.Paciente.CanManagePaciente
    @GetMapping(value = "/{id}")
    public ResponseEntity<PacienteResponse> getPaciente(Long id) {

        Optional<PacienteResponse> response = pacienteService.findById(id);
        return response.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Override
    @CheckSecurity.Paciente.CanManagePaciente
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PacienteResponse> atualizaPaciente(Long id, AtualizaPacienteRequest request) {

        Optional<PacienteResponse> response = pacienteService.atualizaPaciente(id, pacienteMapper.fromRequest(request));
        return response.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Override
    @CheckSecurity.Paciente.CanManagePaciente
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deletaPaciente(Long id) {

        try {
            pacienteService.deletaPaciente(id);
            return ResponseEntity.ok("Usuário deletado com sucesso");
        } catch (EmptyResultDataAccessException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    @CheckSecurity.Paciente.CanViewPacienteAdmin
    @GetMapping("/findByEstagiarioId")
    public ResponseEntity<List<PacienteResponse>> getPacientesByIdEstagiario(Long id){

        List<PacienteResponse> response = pacienteService.findByEstagiarioId(id);
        return (response != null) ? ResponseEntity.ok(response) : ResponseEntity.notFound().build();

    }

    @Override
    @CheckSecurity.Paciente.CanViewPacienteAdmin
    @GetMapping("/findByEstagiarioEmpty")
    public ResponseEntity<List<PacienteResponse>> getPacientesByEstagiarioEmpty(){
        List<PacienteResponse> response = pacienteService.findByEstagiarioEmpty();
        return (response != null) ? ResponseEntity.ok(response) : ResponseEntity.notFound().build();
    }

}

