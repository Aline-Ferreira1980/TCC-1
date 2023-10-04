package br.uscs.gestao_agenda_backend.infrastructure.web;

import br.uscs.gestao_agenda_backend.application.common.SalaMapper;
import br.uscs.gestao_agenda_backend.application.dto.SalaResponse;
import br.uscs.gestao_agenda_backend.application.port.SalaService;
import br.uscs.gestao_agenda_backend.application.request.AtualizaSalaRequest;
import br.uscs.gestao_agenda_backend.application.request.SalaRequest;
import br.uscs.gestao_agenda_backend.infrastructure.security.permissions.CheckSecurity;
import br.uscs.gestao_agenda_backend.infrastructure.web.openapi.SalaControllerOpenApi;
import lombok.AllArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@CrossOrigin
@RequestMapping("sala")
//@SecurityRequirement(name = "BearerAuth") swagger
public class SalaController implements SalaControllerOpenApi {

    private final SalaService salaService;
    private final SalaMapper salaMapper;


    @Override
    @CheckSecurity.Sala.CanCreateAndDeleteSala
    @PostMapping(value = "/cadastrar", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SalaResponse> cadastrarSala(SalaRequest request, UriComponentsBuilder uriBuilder) {
        SalaResponse response = salaService.cadastraSala(salaMapper.fromRequest(request));
        URI uri = uriBuilder.path("/sala/{id}").buildAndExpand(response.getId()).toUri();
        return ResponseEntity.created(uri).body(response);
    }

    @Override
    @GetMapping("/listar")
    public ResponseEntity<List<SalaResponse>> getAllSala() {
        List<SalaResponse> response = salaService.findAll();
        return ResponseEntity.ok(response);
    }

    @Override
    @GetMapping(value = "/{id}")
    public ResponseEntity<SalaResponse> getSalaById(
            Long id) {

        Optional<SalaResponse> response = salaService.findById(id);
        return response.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }


    @Override
    @CheckSecurity.Sala.CanCreateAndDeleteSala
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SalaResponse> atualizaSala(Long id, AtualizaSalaRequest request) {

        Optional<SalaResponse> response = salaService.atualizaSala(id, salaMapper.fromRequest(request));
        return response.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Override
    @CheckSecurity.Sala.CanCreateAndDeleteSala
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deletaSala(Long id) {

        try {
            salaService.deletaSala(id);
            return ResponseEntity.ok("Sala deletado com sucesso");
        } catch (EmptyResultDataAccessException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
