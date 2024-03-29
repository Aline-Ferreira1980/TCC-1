package br.uscs.gestao_agenda_backend.application.email;


import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Singular;

import java.util.Map;
import java.util.Set;

@Getter
@Builder
public class Mensagem {
    @Singular
    private Set<String> destinatarios;

    @NonNull
    private String assunto;

    @NonNull
    private String corpo;

    @Singular("variavel")
    private Map<String, Object> variaveis;

}
