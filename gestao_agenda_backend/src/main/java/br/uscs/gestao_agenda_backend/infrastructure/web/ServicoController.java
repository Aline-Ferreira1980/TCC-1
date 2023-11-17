package br.uscs.gestao_agenda_backend.infrastructure.web;

import br.uscs.gestao_agenda_backend.application.dto.ServicoResponse;
import br.uscs.gestao_agenda_backend.application.port.ServicoService;
import br.uscs.gestao_agenda_backend.application.request.ServicoRequest;
import br.uscs.gestao_agenda_backend.infrastructure.security.permissions.CheckSecurity;
import br.uscs.gestao_agenda_backend.infrastructure.web.openapi.ServicoControllerOpenApi;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@CrossOrigin
@RequestMapping("servico")
public class ServicoController implements ServicoControllerOpenApi {

    private final ServicoService servicoService;

    @Override
    @CheckSecurity.Servico.CanCreateDeleteServico
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ServicoResponse> cadastrarSerico(ServicoRequest request, UriComponentsBuilder uriBuilder) {


        Optional<ServicoResponse> response = servicoService.cadastrarServico(request);
        return response.map(servicoResponse -> {
            URI uri = uriBuilder.path("/{id}").buildAndExpand(servicoResponse.getId()).toUri();
            return ResponseEntity.created(uri).body(servicoResponse);
        }).orElseGet(() -> ResponseEntity.status(HttpStatus.CONFLICT).build());

    }

    @Override
    @CheckSecurity.Servico.CanViewServico
    @GetMapping(value = "/{id}")
    public ResponseEntity<ServicoResponse> getServicoById(Long id) {

        Optional<ServicoResponse> response = servicoService.findById(id);
        return response.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }


    @Override
    @CheckSecurity.Servico.CanViewServico
    @GetMapping(value = "/listar")
    public ResponseEntity<List<ServicoResponse>> getAllServico() {
        List<ServicoResponse> response = servicoService.findAll();
        return ResponseEntity.ok(response);
    }

    @Override
    @CheckSecurity.Servico.CanCreateDeleteServico
    @PutMapping("/{id}")
    public ResponseEntity<ServicoResponse> updateServico(Long id, ServicoRequest request) {

        Optional<ServicoResponse> response = servicoService.udpateServico(id, request);
        return response.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Override
    @CheckSecurity.Servico.CanCreateDeleteServico
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteServico(Long id) {

        try {
            servicoService.deletaServico(id);
            return ResponseEntity.ok("Serviço deletado com sucesso");
        } catch (EmptyResultDataAccessException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    @CheckSecurity.Servico.CanAddRemoveServicoToUser
    @PostMapping(value = "/{id_servico}/add_estagiario", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<ServicoResponse> addEstagiarioToService(Long id_servico, Long id_estagiario) {

        Optional<ServicoResponse> response = servicoService.addEstagiarioToServico(
                id_servico, id_estagiario);

        return response.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());

    }

    @Override
    @CheckSecurity.Servico.CanAddRemoveServicoToUser
    @DeleteMapping("{id_servico}/remove_estagiario")
    public ResponseEntity<ServicoResponse> removeServicoFromEstagiario(Long id_servico, Long id_estagiario) {

        Optional<ServicoResponse> response = servicoService.removeEstagiarioFromServico(id_servico, id_estagiario);
        return response.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Override
    @CheckSecurity.Servico.CanAddRemoveServicoToUserAdmin
    @PostMapping(value="/{id_servico}/add_docente", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<ServicoResponse> addDocenteToService(Long id_servico, Long id_docente) {

        Optional<ServicoResponse> response = servicoService.addDocenteToServico(
                id_servico, id_docente);

        return response.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());

    }

    @Override
    @CheckSecurity.Servico.CanAddRemoveServicoToUserAdmin
    @DeleteMapping("{id_servico}/remove_docente")
    public ResponseEntity<ServicoResponse> removeServicoFromDocente(Long id_servico, Long id_docente) {

        Optional<ServicoResponse> response = servicoService.removeDocenteFromServico(id_servico, id_docente);
        return response.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Override
    @CheckSecurity.Servico.CanAddRemoveServicoToUserAdmin
    @PostMapping(value = "/{id_servico}/add_estagiarios")
    public ResponseEntity<ServicoResponse> addEstagiariosToService(Long id_servico, List<Long> id_estagiarios) {

        Optional<ServicoResponse> response = servicoService.addEstagiariosToServico(
                id_servico, id_estagiarios);

        return response.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());

    }

    @Override
    @CheckSecurity.Servico.CanAddRemoveServicoToUserAdmin
    @PostMapping(value = "/{id_servico}/add_docentes")
    public ResponseEntity<ServicoResponse> addDocentesToService(Long id_servico, List<Long> id_docentes) {

        Optional<ServicoResponse> response = servicoService.addDocentesToServico(
                id_servico, id_docentes);

        return response.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());

    }

}