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
        if (request == null) {
            return true; // Deixa validações @NotNull fazerem seu trabalho
        }

        boolean valid = true;
        context.disableDefaultConstraintViolation();

        // Validar se data de fabricação não é posterior à data de validade
        if (request.getDataFabricacao() != null && request.getDataValidade() != null) {
            if (request.getDataFabricacao().isAfter(request.getDataValidade())) {
                context.buildConstraintViolationWithTemplate("Data de fabricação não pode ser posterior à data de validade")
                        .addPropertyNode("dataFabricacao")
                        .addConstraintViolation();
                valid = false;
            }
        }

        // Validar se data de fabricação não é futura
        if (request.getDataFabricacao() != null && request.getDataFabricacao().isAfter(java.time.LocalDate.now())) {
            context.buildConstraintViolationWithTemplate("Data de fabricação não pode ser futura")
                    .addPropertyNode("dataFabricacao")
                    .addConstraintViolation();
            valid = false;
        }

        return valid;
    }
}
