package br.uscs.gestao_agenda_backend.exception;

import java.io.Serial;

public class ServicoBindingException extends RuntimeException{

    @Serial
    private static final long serialVersionUID = 1L;
    public ServicoBindingException(){
        super();
    }
    public ServicoBindingException(String message){
        super(message);
    }
}
