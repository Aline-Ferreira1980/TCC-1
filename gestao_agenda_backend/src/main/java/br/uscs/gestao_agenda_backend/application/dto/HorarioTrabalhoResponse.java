package br.uscs.gestao_agenda_backend.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class HorarioTrabalhoResponse {

    @Schema(type = "integer", example = "1")
    private DayOfWeek diaSemana;
    @Schema(type = "string", example = "08:00:00")
    private LocalTime horarioInicio;
    @Schema(type = "string", example = "17:00:00")
    private LocalTime horarioFim;
}
