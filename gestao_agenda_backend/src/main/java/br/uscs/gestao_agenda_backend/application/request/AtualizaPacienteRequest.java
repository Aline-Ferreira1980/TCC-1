package br.uscs.gestao_agenda_backend.application.request;

import br.uscs.gestao_agenda_backend.application.request.validation.Enum;
import br.uscs.gestao_agenda_backend.application.request.validation.NotNullIfAnotherFieldIsTrue;
import br.uscs.gestao_agenda_backend.domain.model.Endereco;
import br.uscs.gestao_agenda_backend.domain.model.Telefone;
import br.uscs.gestao_agenda_backend.domain.model.enums.EstadoCivil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Set;


@AllArgsConstructor
@NoArgsConstructor
@Data
@NotNullIfAnotherFieldIsTrue(baseFieldName = "isMenorIdade", checkedFieldName = "relacaoRepresentante",
        message = "Se o campo isMenorIdade for verdadeiro, é obrigatório especificar relacaoRepresentante")
@NotNullIfAnotherFieldIsTrue(baseFieldName = "isMenorIdade", checkedFieldName = "representanteNome",
        message = "Se o campo isMenorIdade for verdadeiro, é obrigatório especificar representanteNome")
public class AtualizaPacienteRequest {

    @NonNull
    @NotBlank
    private String nome;

    @NonNull
    @NotBlank
    private String sobrenome;

    @NonNull
    @NotBlank
    @Size(min = 6, message = "A senha deve conter pelo menos 6 caracteres.")
    private String senha;

    private String nomeSocial;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dataNascimento;

    @NonNull
    @NotBlank
    private String genero;

    @NonNull
    @Enum(enumClass = EstadoCivil.class, message = "O campo 'estadoCivil' não é válido.")
    private EstadoCivil estadoCivil;

    @NonNull
    @NotBlank
    private String etniaRacial;

    @NotNull
    private Endereco endereco;

    @NotEmpty
    private Set<Telefone> telefone;

    @NonNull
    private Boolean isMenorIdade;

    private String relacaoRepresentante;
    private String representanteNome;

    // TODO: Ajustar logica de negocio para que se o usuário for menor os campos abaixo precisa sem preenchido
}
