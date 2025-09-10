package fiap.msmedicamentos.adapter.web.medicamento.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = ValidMedicamentoValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidMedicamento {
    String message() default "Dados do medicamento são inválidos";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
