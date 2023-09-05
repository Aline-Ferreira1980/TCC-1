package br.uscs.gestao_agenda_backend.application.dto;

import java.time.LocalDateTime;

public class AgendamentoPropertyResponse {


    private Long id;

    private EstagiarioPropertyResponse estagiario;

    private PacientePropertyResponse paciente;

    private SalaPropertyResponse sala;

    private LocalDateTime dataConsulta;
}

