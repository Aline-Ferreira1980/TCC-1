package br.uscs.gestao_agenda_backend.application.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.time.LocalDateTime;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class AgendamentoRequest {

    @NonNull
    @NotBlank
    @Email(message = "O e-mail deve estar no formato válido.")
    @Pattern(regexp = "^[a-zA-Z0-9_.+-]+@uscsonline\\.com\\.br$",
            message = "O e-mail deve estar no formato <usuário>@uscsonline.com.br")
    private String estagiarioEmail;

    @NonNull
    @NotBlank
    @Email(message = "O e-mail deve estar no formato válido.")
    private String pacienteEmail;

    @NotNull(message = "O campo 'id' não pode ser nulo.")
    @Min(value = 1, message = "O campo 'id' deve ser maior ou igual a 1.")
    private Long salaId;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @FutureOrPresent(message = "O campo 'inicioAgendamento' deve ser um dia válido.")
    private LocalDateTime inicioAgendamento;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @Future(message = "O campo 'fimAgendamento' deve ser um dia válido.")
    private LocalDateTime fimAgendamento;
}
