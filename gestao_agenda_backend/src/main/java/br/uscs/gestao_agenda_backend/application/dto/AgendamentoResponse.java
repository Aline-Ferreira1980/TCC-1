package br.uscs.gestao_agenda_backend.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class AgendamentoResponse {

    private Long id;

    private EstagiarioPropertyResponse estagiario;
    private PacientePropertyResponse paciente;
    private SalaPropertyResponse sala;
    private LocalDateTime inicioAgendamento;
    private LocalDateTime fimAgendamento;
}
