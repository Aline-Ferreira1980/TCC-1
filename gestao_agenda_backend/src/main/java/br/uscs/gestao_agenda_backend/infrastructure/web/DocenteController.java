package br.uscs.gestao_agenda_backend.infrastructure.web;

import br.uscs.gestao_agenda_backend.application.common.DocenteMapper;
import br.uscs.gestao_agenda_backend.application.dto.DocenteResponse;
import br.uscs.gestao_agenda_backend.application.port.DocenteService;
import br.uscs.gestao_agenda_backend.application.request.AtualizaDocenteRequest;
import br.uscs.gestao_agenda_backend.application.request.CadastroDocenteRequest;
import br.uscs.gestao_agenda_backend.infrastructure.security.permissions.CheckSecurity;
import br.uscs.gestao_agenda_backend.infrastructure.web.openapi.DocenteControllerOpenApi;
import lombok.AllArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;


@RestController
@AllArgsConstructor
@RequestMapping("docente")
@CrossOrigin
public class DocenteController implements DocenteControllerOpenApi {

    private final DocenteService docenteService;
    private final DocenteMapper docenteMapper;


    @Override
    @CheckSecurity.Docente.CanManageDocente
    @PostMapping(value = "/cadastrar", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DocenteResponse> cadastrarDocente(CadastroDocenteRequest request,
                                                            UriComponentsBuilder uriBuilder) {
        DocenteResponse response = docenteService.cadastraDocente(docenteMapper.fromRequest(request));
        URI uri = uriBuilder.path("/docente/{id}").buildAndExpand(response.getId()).toUri();
        return ResponseEntity.created(uri).body(response);
    }

    @Override
    @GetMapping("/listar")
    public ResponseEntity<List<DocenteResponse>> getAllDocente() {
        List<DocenteResponse> response = docenteService.findAll();
        return ResponseEntity.ok(response);
    }

    @Override
    @GetMapping(value = "/{id}")
    public ResponseEntity<DocenteResponse> getDocenteById(Long id) {
        Optional<DocenteResponse> response = docenteService.findById(id);
        return response.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Override
    @CheckSecurity.Docente.CanManageDocente
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DocenteResponse> atualizaDocente(Long id, AtualizaDocenteRequest request) {
        Optional<DocenteResponse> response = docenteService.atualizaDocente(id, docenteMapper.fromRequest(request));
        return response.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Override
    @CheckSecurity.Docente.CanManageDocente
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deletaDocente(Long id) {
        try {
            docenteService.deletaDocente(id);
            return ResponseEntity.ok("Docente deletado com sucesso");
        } catch (EmptyResultDataAccessException e) {
            return ResponseEntity.notFound().build();
        }
    }


    @Override
    @GetMapping("/servico")
    public ResponseEntity<List<DocenteResponse>> listDocenteByServico(String acronimo) {
        List<DocenteResponse> response = docenteService.findByServico(acronimo);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @Override
    @GetMapping("/servico_vazio")
    public ResponseEntity<List<DocenteResponse>> listDocenteServicoEmpty() {
        List<DocenteResponse> response = docenteService.findServicoEmpty();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
