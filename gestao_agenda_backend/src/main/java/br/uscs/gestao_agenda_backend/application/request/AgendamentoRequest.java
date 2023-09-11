package br.uscs.gestao_agenda_backend.application.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class AgendamentoRequest {

    private String estagiarioEmail;

    private String pacienteEmail;

    private Long salaId;

    private LocalDateTime inicioAgendamento;

    private LocalDateTime fimAgendamento;
}
