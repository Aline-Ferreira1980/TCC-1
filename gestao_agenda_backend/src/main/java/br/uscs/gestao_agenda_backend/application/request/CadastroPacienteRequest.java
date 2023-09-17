package br.uscs.gestao_agenda_backend.application.request;

import br.uscs.gestao_agenda_backend.domain.model.Endereco;
import br.uscs.gestao_agenda_backend.domain.model.Telefone;
import br.uscs.gestao_agenda_backend.domain.model.enums.EstadoCivil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CadastroPacienteRequest {

    @NonNull
    @NotBlank
    private String nome;

    @NonNull
    @NotBlank
    private String sobrenome;

    @NonNull
    @NotBlank
    @Email
    private String email;

    @NonNull
    @NotBlank
    private String senha;

    private String nomeSocial;

    private LocalDate dataNascimento;

    @NonNull
    @NotBlank
    private String genero;

    @NonNull
    private EstadoCivil estadoCivil;

    private String etniaRacial;

    private Endereco endereco;

    private Set<Telefone> telefone;

    @NonNull
    private Boolean isMenorIdade;
    private String relacaoRepresentante;
    private String representanteNome;
}
