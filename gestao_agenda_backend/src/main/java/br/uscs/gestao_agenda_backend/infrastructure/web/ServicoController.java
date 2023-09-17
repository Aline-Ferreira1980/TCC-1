package br.uscs.gestao_agenda_backend.infrastructure.web;

import br.uscs.gestao_agenda_backend.application.dto.AgendamentoResponse;
import br.uscs.gestao_agenda_backend.application.dto.DocenteResponse;
import br.uscs.gestao_agenda_backend.application.dto.EstagiarioResponse;
import br.uscs.gestao_agenda_backend.application.dto.ServicoResponse;
import br.uscs.gestao_agenda_backend.application.port.ServicoService;
import br.uscs.gestao_agenda_backend.application.request.AgendamentoRequest;
import br.uscs.gestao_agenda_backend.application.request.AtualizaEstagiarioRequest;
import br.uscs.gestao_agenda_backend.application.request.ServicoRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("servico")
@Tag(name = "Serviço")
public class ServicoController {

    private final ServicoService servicoService;

    @Operation(summary = "Cria novo serviço na aplicação", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Serviço criado com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "401", description = "Usuário nao autenticado"),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar busca dos dados"),
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> cadastrarSerico(@RequestBody ServicoRequest request,
                                               UriComponentsBuilder uriBuilder) {

        try {
            Optional<ServicoResponse> response = servicoService.cadastrarServico(request);
            if (response.isPresent()) {

                URI uri = uriBuilder.path("/{id}").buildAndExpand(response.get().getId()).toUri();
                return ResponseEntity.created(uri).body(response.get());
            }
            return ResponseEntity.badRequest().build();
        } catch (Exception ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
    @Operation(summary = "Realiza busca serviço por id", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "404", description = "Docente não encontrado"),
            @ApiResponse(responseCode = "401", description = "Usuário nao autenticado"),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar busca dos dados"),
    })
    @GetMapping(value = "/{id}")
    public ResponseEntity<ServicoResponse> getServicoById(@PathVariable Long id){
        Optional<ServicoResponse> response = servicoService.findById(id);
        return response.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }


    @Operation(summary = "Lista todos os serviços cadastrados na aplicação", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "404", description = "Docente não encontrado"),
            @ApiResponse(responseCode = "401", description = "Usuário nao autenticado"),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar busca dos dados"),
    })
    @GetMapping(value = "/listar")
    public ResponseEntity<List<ServicoResponse>> getAllServico(){
        List<ServicoResponse> response = servicoService.findAll();
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Atualiza Serviço na aplicação", method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Serviço atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Serviço nao encontrado"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "401", description = "Usuário nao autenticado"),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar busca dos dados"),
    })
    @PutMapping("/{id}")
    public ResponseEntity<ServicoResponse> updateServico(
            @PathVariable Long id,
            @RequestBody ServicoRequest request){

        Optional<ServicoResponse> response = servicoService.udpateServico(id, request);
        return response.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Deleta serviço da aplicação", method = "DELETE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Serviço removido com sucesso"),
            @ApiResponse(responseCode = "404", description = "Serviço nao encontrado"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "401", description = "Usuário nao autenticado"),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar busca dos dados"),
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteServico(@PathVariable Long id){
        try {
            servicoService.deletaServico(id);
            return ResponseEntity.ok("Serviço deletado com sucesso");
        }catch (EmptyResultDataAccessException e){
            return ResponseEntity.notFound().build();
        }
    }

//    @Operation(summary = "Cria novo serviço na aplicação", method = "POST")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "201", description = "Serviço criado com sucesso"),
//            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
//            @ApiResponse(responseCode = "401", description = "Usuário nao autenticado"),
//            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos"),
//            @ApiResponse(responseCode = "500", description = "Erro ao realizar busca dos dados"),
//    })
//    @PostMapping(value="/{service_id}/addEstagiario" ,consumes = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<?> addEstagiarioToService(@RequestBody ServicoRequest request,
//                                             UriComponentsBuilder uriBuilder) {
//
//        try {
//            Optional<ServicoResponse> response = servicoService.cadastrarServico(request);
//            if (response.isPresent()) {
//
//                URI uri = uriBuilder.path("/{id}").buildAndExpand(response.get().getId()).toUri();
//                return ResponseEntity.created(uri).body(response.get());
//            }
//            return ResponseEntity.badRequest().build();
//        } catch (Exception ex){
//            return ResponseEntity.badRequest().body(ex.getMessage());
//        }
//    }


}
