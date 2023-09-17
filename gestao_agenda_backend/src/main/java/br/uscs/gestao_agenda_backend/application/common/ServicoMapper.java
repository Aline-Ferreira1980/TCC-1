package br.uscs.gestao_agenda_backend.application.common;

import br.uscs.gestao_agenda_backend.application.dto.DocentePropertyResponse;
import br.uscs.gestao_agenda_backend.application.dto.EstagiarioPropertyResponse;
import br.uscs.gestao_agenda_backend.application.dto.ServicoPropertyResponse;
import br.uscs.gestao_agenda_backend.application.dto.ServicoResponse;
import br.uscs.gestao_agenda_backend.application.request.ServicoRequest;
import br.uscs.gestao_agenda_backend.domain.model.Docente;
import br.uscs.gestao_agenda_backend.domain.model.Estagiario;
import br.uscs.gestao_agenda_backend.domain.model.Servico;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class ServicoMapper {
    private final ModelMapper modelMapper;
    EstagiarioMapper estagiarioMapper;


    public ServicoResponse toResponse(Servico servico) {
        DocenteMapper docenteMapper = new DocenteMapper(this.modelMapper);
        Set<EstagiarioPropertyResponse> estagiariosResponse = new HashSet<>();
        if (servico.getEstagiarios() != null) {
            for (Estagiario e : servico.getEstagiarios()) {
                estagiariosResponse.add(estagiarioMapper.toPropertyResponse(e));
            }
        }
        Set<DocentePropertyResponse> docentesResponse = new HashSet<>();
        if (servico.getDocentes() != null) {
            for (Docente d : servico.getDocentes()) {
                docentesResponse.add(docenteMapper.toPropertyResponse(d));
            }
        }

        ServicoResponse response = ServicoResponse.builder()
                .id(servico.getId())
                .acronimo(servico.getAcronimo())
                .nome(servico.getNome())
                .descricao(servico.getDescricao())
                .estagiarios(estagiariosResponse)
                .docentes(docentesResponse)
                .build();

        return response;
    }

    public ServicoPropertyResponse toPropertyResponse(Servico servico) {
        return modelMapper.map(servico, ServicoPropertyResponse.class);
    }

    public Set<ServicoPropertyResponse> toPropertyResponseSet(Set<Servico> servicos) {
        if (servicos != null) {
            return servicos.stream()
                    .map(this::toPropertyResponse)
                    .collect(Collectors.toSet());
        }
        return null;
    }

    public Servico fromRequest(ServicoRequest request) {
        return modelMapper.map(request, Servico.class);
    }
}