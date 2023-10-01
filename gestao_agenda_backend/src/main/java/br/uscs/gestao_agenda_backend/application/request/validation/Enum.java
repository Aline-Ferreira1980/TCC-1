package br.uscs.gestao_agenda_backend.application.request.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Enum {
    Class<? extends java.lang.Enum<?>> enumClass();
    String message() default "O valor não é válido.";
}
