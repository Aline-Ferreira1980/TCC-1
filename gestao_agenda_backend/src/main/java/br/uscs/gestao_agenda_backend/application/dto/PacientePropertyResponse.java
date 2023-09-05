package br.uscs.gestao_agenda_backend.application.dto;

import br.uscs.gestao_agenda_backend.domain.model.Agendamento;
import br.uscs.gestao_agenda_backend.domain.model.Endereco;
import br.uscs.gestao_agenda_backend.domain.model.Estagiario;
import br.uscs.gestao_agenda_backend.domain.model.Telefone;
import br.uscs.gestao_agenda_backend.domain.model.enums.EstadoCivil;

import java.time.LocalDate;
import java.util.Set;

public class PacientePropertyResponse {
    private Long id;
    private String nome;
    private String sobrenome;
    private String nomeSocial;

}