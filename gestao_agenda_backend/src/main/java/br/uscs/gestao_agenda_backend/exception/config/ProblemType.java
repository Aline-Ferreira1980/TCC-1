package br.uscs.gestao_agenda_backend.exception.config;

import lombok.Getter;

@Getter
public enum ProblemType {
    ACESSO_NEGADO("/acesso-negado", "Acesso negado"),
    DADOS_INVALIDOS("/dados-invalidos", "Dados inválidos"),
    ERRO_DE_SISTEMA("/erro-de-sistema", "Erro de sistema"),
    PARAMETRO_INVALIDO("/parametro-invalido", "Parâmetro inválido"),
    MENSAGEM_INCOMPREENSIVEL("/mensagem-incompreensivel", "Mensagem incompreensível"),
    RECURSO_NAO_ENCONTRADO("/recurso-nao-encontrado", "Recurso não encontrado"),
    ENTIDADE_EM_USO("/entidade-em-uso", "Entidade em uso"),
    ERRO_NEGOCIO("/erro-negocio", "Violação de regra de negócio");

    private final String title;
    private final String uri;

    ProblemType(String path, String title) {
        this.uri = "${server.apiUrl}" + path;
        this.title = title;
    }
}
