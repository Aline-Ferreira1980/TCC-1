package br.uscs.gestao_agenda_backend.exception;

import java.io.Serial;

public class InvalidAgendamentoArgumentException extends AgendamentoException{
    @Serial
    private static final long serialVersionUID = 1L;
    public InvalidAgendamentoArgumentException(String message){
        super(message);
    }
}
