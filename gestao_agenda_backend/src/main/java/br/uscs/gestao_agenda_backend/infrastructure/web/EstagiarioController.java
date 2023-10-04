package br.uscs.gestao_agenda_backend.infrastructure.web;

import br.uscs.gestao_agenda_backend.application.dto.EstagiarioResponse;
import br.uscs.gestao_agenda_backend.application.port.EstagiarioService;
import br.uscs.gestao_agenda_backend.application.request.AtualizaEstagiarioRequest;
import br.uscs.gestao_agenda_backend.application.request.CadastroEstagiarioRequest;
import br.uscs.gestao_agenda_backend.infrastructure.web.openapi.EstagiarioControllerOpenApi;
import lombok.AllArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@CrossOrigin
@RequestMapping(value = "estagiario", produces = {"application/json"})
public class EstagiarioController implements EstagiarioControllerOpenApi {

    private final EstagiarioService estagiarioService;

    @Override
    @GetMapping("/disponibilidade")
    public ResponseEntity<List<EstagiarioResponse>> getAvailabilityByDateRange(LocalDate startDate, LocalDate endDate) {
        List<EstagiarioResponse> response = estagiarioService.getAllAvailableInDataRange(startDate, endDate);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    @PostMapping(value = "/cadastrar", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EstagiarioResponse> cadastrarEstagiario(CadastroEstagiarioRequest request,
                                                                  UriComponentsBuilder uriBuilder) {

        EstagiarioResponse response = estagiarioService.cadastrarEstagiario(request);
        URI uri = uriBuilder.path("/transacoes/{id}").buildAndExpand(response.getId()).toUri();
        return ResponseEntity.created(uri).body(response);
    }

    @Override
    @GetMapping(value = "/{id}")
    public ResponseEntity<EstagiarioResponse> getEstagiarioById(Long id) {

        Optional<EstagiarioResponse> response = estagiarioService.findById(id);
        return response.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Override
    @GetMapping("/listar")
    public ResponseEntity<List<EstagiarioResponse>> getAllEstagiarios() {
        List<EstagiarioResponse> response = estagiarioService.findAll();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<EstagiarioResponse> updateEstagiario(Long id, AtualizaEstagiarioRequest request) {

        Optional<EstagiarioResponse> response = estagiarioService.updateEstagiario(id, request);
        return response.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEstagiario(Long id) {

        try {
            estagiarioService.deletaEstagiario(id);
            return ResponseEntity.ok("Estagiario deletado com sucesso");
        } catch (EmptyResultDataAccessException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    @GetMapping("/servico")
    public ResponseEntity<List<EstagiarioResponse>> listEstagiariosByServico(String acronimo) {

        List<EstagiarioResponse> response = estagiarioService.findByServico(acronimo);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    @PostMapping(value = "{idEstagiario}/add_docente", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<EstagiarioResponse> addDocenteToEstagiario(Long idEstagiario, Long idDocente) {

        Optional<EstagiarioResponse> response = estagiarioService.addDocente(idEstagiario, idDocente);
        return response.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Override
    @DeleteMapping(value = "{idEstagiario}/remove_docente")
    public ResponseEntity<EstagiarioResponse> removeDocenteFromEstagiario(Long idEstagiario) {

        Optional<EstagiarioResponse> response = estagiarioService.removeDocente(idEstagiario);
        return response.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Override
    @GetMapping("/servico_vazio")
    public ResponseEntity<List<EstagiarioResponse>> listEstagiarioServicoEmpty() {
        List<EstagiarioResponse> response = estagiarioService.findServicoEmpty();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    @GetMapping("/docente_vazio")
    public ResponseEntity<List<EstagiarioResponse>> listEstagiarioDocenteEmpty() {
        List<EstagiarioResponse> response = estagiarioService.findDocenteNull();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}