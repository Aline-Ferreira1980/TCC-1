package br.uscs.gestao_agenda_backend.exception;

import java.io.Serial;

public class AgendamentoConflictException extends AgendamentoException {
    @Serial
    private static final long serialVersionUID = 1L;
    public AgendamentoConflictException(String mensagem) {
        super(mensagem);
    }

}
