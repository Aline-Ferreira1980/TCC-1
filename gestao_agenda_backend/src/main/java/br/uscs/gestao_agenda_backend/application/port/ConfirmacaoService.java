package br.uscs.gestao_agenda_backend.application.port;

public interface ConfirmacaoService {
    void enviarEmailConfirmacao(String email, String token);

    void confirmarEmail(String token);

}
