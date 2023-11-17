package br.uscs.gestao_agenda_backend.exception.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;


@Getter
public enum ProblemType {
    ACESSO_NEGADO("/acesso-negado", "Acesso negado"),
    DADOS_INVALIDOS("/dados-invalidos", "Dados inválidos"),
    ERRO_DE_SISTEMA("/erro-de-sistema", "Erro de sistema"),
    PARAMETRO_INVALIDO("/parametro-invalido", "Parâmetro inválido"),
    MENSAGEM_INCOMPREENSIVEL("/mensagem-incompreensivel", "Mensagem incompreensível"),
    RECURSO_NAO_ENCONTRADO("/recurso-nao-encontrado", "Recurso não encontrado"),
    ENTIDADE_EM_USO("/entidade-em-uso", "Entidade em uso"),
    ERRO_NEGOCIO("/erro-negocio", "Violação de regra de negócio"),
    ENTIDADE_NAO_ENCONTRADA("/entidade-nao-encontrada","Entidade nao encontrada" ),
    CONVIDADO_OCUPADO("/convidado-ocupado","Convidado ocupado" ),
    ARGUMENTO_AGENDAMENTO_INVALIDO("/argumento-agendamento-invalido","Argumento de agendamento invalido" ),
    ERRO_VINCULAR_SERVICO("/erro-vincular-servico","Erro ao vincular Servico" );


    private final String title;
    private final String uri;

    @Value("${server.apiUrl}")
    private String apiUrl;

    ProblemType(String path, String title) {
        this.uri = apiUrl + path;
        this.title = title;
    }
}
