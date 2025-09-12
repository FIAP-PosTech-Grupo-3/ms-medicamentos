package fiap.msmedicamentos.core.estoque.service;

import fiap.msmedicamentos.core.estoque.exception.EstoqueInvalidoException;
import fiap.msmedicamentos.core.medicamento.gateway.MedicamentoGateway;
import fiap.msmedicamentos.core.unidadesaude.gateway.UnidadeSaudeGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EstoqueValidationService {

    private final MedicamentoGateway medicamentoGateway;
    private final UnidadeSaudeGateway unidadeSaudeGateway;

    public void validarQuantidadePositiva(Integer quantidade) {
        if (quantidade == null || quantidade <= 0) {
            throw new EstoqueInvalidoException("Quantidade deve ser maior que zero");
        }
    }

    public void validarQuantidadeNaoNegativa(Integer quantidade) {
        if (quantidade == null || quantidade < 0) {
            throw new EstoqueInvalidoException("Quantidade deve ser maior ou igual a zero");
        }
    }

    public void validarExistenciaMedicamentoEUnidade(Long medicamentoId, Long unidadeSaudeId) {
        if (!medicamentoGateway.existePorId(medicamentoId)) {
            throw new EstoqueInvalidoException("Medicamento não encontrado com ID: " + medicamentoId);
        }

        if (!unidadeSaudeGateway.existePorId(unidadeSaudeId)) {
            throw new EstoqueInvalidoException("Unidade de saúde não encontrada com ID: " + unidadeSaudeId);
        }
    }
}
