package fiap.msmedicamentos.core.estoque.usecase;

import fiap.msmedicamentos.core.estoque.entity.EstoqueMedicamento;
import fiap.msmedicamentos.core.estoque.exception.EstoqueInvalidoException;
import fiap.msmedicamentos.core.estoque.gateway.EstoqueGateway;
import fiap.msmedicamentos.core.estoque.service.EstoqueValidationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AtualizarEstoqueUseCase {

    private final EstoqueGateway estoqueGateway;
    private final EstoqueValidationService validationService;

    public EstoqueMedicamento execute(Long medicamentoId, Long unidadeSaudeId, Integer novaQuantidade, Integer quantidadeMinima) {
        log.info("Atualizando estoque do medicamento {} na unidade {} para {}", medicamentoId, unidadeSaudeId, novaQuantidade);
        
        validationService.validarQuantidadeNaoNegativa(novaQuantidade);
        validationService.validarExistenciaMedicamentoEUnidade(medicamentoId, unidadeSaudeId);

        var estoqueOpt = estoqueGateway.buscarPorMedicamentoEUnidade(medicamentoId, unidadeSaudeId);
        
        EstoqueMedicamento estoque;
        if (estoqueOpt.isPresent()) {
            estoque = estoqueOpt.get();
            estoque.definirQuantidade(novaQuantidade);
            if (quantidadeMinima != null) {
                estoque.setQuantidadeMinima(quantidadeMinima);
            }
        } else {
            estoque = new EstoqueMedicamento();
            estoque.setMedicamentoId(medicamentoId);
            estoque.setUnidadeSaudeId(unidadeSaudeId);
            estoque.definirQuantidade(novaQuantidade);
            estoque.setQuantidadeMinima(quantidadeMinima != null ? quantidadeMinima : 10); // Padrão 10
        }

        if (!estoque.isValido()) {
            throw new EstoqueInvalidoException("Dados do estoque são inválidos");
        }

        EstoqueMedicamento estoqueSalvo = estoqueGateway.salvar(estoque);
        log.info("Estoque atualizado para {} unidades", estoqueSalvo.getQuantidade());
        
        return estoqueSalvo;
    }
}
