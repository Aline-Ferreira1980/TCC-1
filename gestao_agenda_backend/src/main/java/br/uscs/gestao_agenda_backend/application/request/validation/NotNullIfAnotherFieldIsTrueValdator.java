package br.uscs.gestao_agenda_backend.application.request.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.BeansException;

public class NotNullIfAnotherFieldIsTrueValdator
        implements ConstraintValidator<NotNullIfAnotherFieldIsTrue, Object> {

    private String baseFieldName;
    private String validatedFieldName;

    @Override
    public void initialize(NotNullIfAnotherFieldIsTrue constraintAnnotation) {
        this.baseFieldName = constraintAnnotation.baseFieldName();
        this.validatedFieldName = constraintAnnotation.checkedFieldName();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        try {
            BeanWrapper object = new BeanWrapperImpl(value);
            Boolean baseFieldValue = (Boolean) object.getPropertyValue(this.baseFieldName);
            String validatedFieldValue = (String) object.getPropertyValue(this.validatedFieldName);

            if (baseFieldValue != null && baseFieldValue) {
                if (validatedFieldValue == null || validatedFieldValue.isBlank()) {
                    context.disableDefaultConstraintViolation();
                    context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
                            .addPropertyNode(this.validatedFieldName)
                            .addConstraintViolation();
                    return false;
                }
            }
        } catch (BeansException e) {
            throw new RuntimeException(e);
        }
        return true;
    }
}
