package br.uscs.gestao_agenda_backend.application.dto.in;

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
public class HorarioTrabalhoRequest {

    private DayOfWeek diaSemana;
    private LocalTime horarioInicio;
    private LocalTime horarioFim;
}
