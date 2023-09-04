package br.uscs.gestao_agenda_backend.application.dto;

import br.uscs.gestao_agenda_backend.domain.model.Estagiario;
import br.uscs.gestao_agenda_backend.domain.model.Servico;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class DocenteResponse {
    private Long id;
    private String nome;
    private String sobrenome;
    private String email;

    private String ruscs;

    private Set<ServicoPropertyResponse> servicos;

    private Set<EstagiarioPropertyResponse> estagiarios;
}
