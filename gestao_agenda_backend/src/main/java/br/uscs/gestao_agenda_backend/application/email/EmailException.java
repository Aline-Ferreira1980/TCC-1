package br.uscs.gestao_agenda_backend.application.email;

public class EmailException extends RuntimeException  {
    private static final long serialVersionUID = 1L;

    public EmailException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmailException(String message) {
        super(message);
    }
}
