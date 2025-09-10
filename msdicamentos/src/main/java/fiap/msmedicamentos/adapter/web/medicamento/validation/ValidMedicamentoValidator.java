package fiap.msmedicamentos.adapter.web.medicamento.validation;

import fiap.msmedicamentos.adapter.web.medicamento.CadastrarMedicamentoRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidMedicamentoValidator implements ConstraintValidator<ValidMedicamento, CadastrarMedicamentoRequest> {

    @Override
    public void initialize(ValidMedicamento constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(CadastrarMedicamentoRequest request, ConstraintValidatorContext context) {
        // Como simplificamos a entidade, apenas validações básicas
        return request != null;
    }
}
