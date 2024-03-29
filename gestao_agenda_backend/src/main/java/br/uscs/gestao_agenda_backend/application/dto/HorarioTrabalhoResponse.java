package br.uscs.gestao_agenda_backend.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.DayOfWeek;
import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class HorarioTrabalhoResponse {
    private Long id;
    private DayOfWeek diaSemana;
    private LocalTime horarioInicio;
    private LocalTime horarioFim;
}
