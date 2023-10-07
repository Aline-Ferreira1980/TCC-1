package br.uscs.gestao_agenda_backend.application.request.validation;

import javax.validation.Constraint;
import javax.validation.Payload;

import java.lang.annotation.Documented;

import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;


@Target({TYPE, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Repeatable(NotNullIfAnotherFieldIsTrue.List.class) // only with hibernate-validator >= 6.x
@Constraint(validatedBy = NotNullIfAnotherFieldIsTrueValdator.class)
@Documented
public @interface NotNullIfAnotherFieldIsTrue {
    String baseFieldName();
    String checkedFieldName();

    String message() default "{NotNullIfAnotherFieldIsTrue.message}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    @Target({TYPE, ANNOTATION_TYPE})
    @Retention(RUNTIME)
    @Documented
    @interface List {
        NotNullIfAnotherFieldIsTrue[] value();
    }
}
