package br.uscs.gestao_agenda_backend.application.dto;


import br.uscs.gestao_agenda_backend.domain.model.*;
import br.uscs.gestao_agenda_backend.domain.model.enums.EstadoCivil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
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

    private List<AgendamentoPropertyResponse> agendamentos;

}
