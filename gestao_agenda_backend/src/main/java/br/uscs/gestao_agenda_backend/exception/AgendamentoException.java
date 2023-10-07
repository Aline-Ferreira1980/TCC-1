package br.uscs.gestao_agenda_backend.exception;

import java.io.Serial;

public class AgendamentoException extends RuntimeException{
    @Serial
    private static final long serialVersionUID = 1L;

    public AgendamentoException() {
        super();
    }
    public AgendamentoException(String message){
        super(message);
    }
}
