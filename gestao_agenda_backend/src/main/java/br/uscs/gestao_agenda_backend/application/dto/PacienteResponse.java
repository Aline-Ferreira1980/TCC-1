package br.uscs.gestao_agenda_backend.application.dto;


import br.uscs.gestao_agenda_backend.domain.model.Endereco;
import br.uscs.gestao_agenda_backend.domain.model.Telefone;
import br.uscs.gestao_agenda_backend.domain.model.enums.EstadoCivil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class PacienteResponse {

    private Long id;
    private String nome;
    private String sobrenome;
    private String email;
    private String nomeSocial;
    private LocalDate dataNascimento;
    private String genero;
    private EstadoCivil estadoCivil;
    private String etniaRacial;
    private Endereco endereco;
    private Set<Telefone> telefone;
    private Boolean isMenorIdade;
    private String relacaoRepresentante;
    private String representanteNome;
    private EstagiarioPropertyResponse estagiario;

}
