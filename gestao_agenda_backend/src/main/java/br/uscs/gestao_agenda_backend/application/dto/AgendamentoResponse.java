package br.uscs.gestao_agenda_backend.application.dto;

import br.uscs.gestao_agenda_backend.domain.model.Estagiario;
import br.uscs.gestao_agenda_backend.domain.model.Paciente;
import br.uscs.gestao_agenda_backend.domain.model.Sala;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

public class AgendamentoResponse {

    private Long id;

    private EstagiarioPropertyResponse estagiario;

    private PacientePropertyResponse paciente;

    private SalaResponse sala;

    private LocalDateTime dataConsulta;
}
