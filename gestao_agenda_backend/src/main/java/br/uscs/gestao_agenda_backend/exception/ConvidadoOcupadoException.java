package br.uscs.gestao_agenda_backend.exception;

import java.io.Serial;

public class ConvidadoOcupadoException extends AgendamentoException {
    @Serial
    private static final long serialVersionUID = 1L;
    public ConvidadoOcupadoException(String mensagem) {
        super(mensagem);
    }

}
