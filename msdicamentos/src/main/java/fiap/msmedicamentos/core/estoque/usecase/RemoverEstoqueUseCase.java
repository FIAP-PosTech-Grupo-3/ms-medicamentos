package fiap.msmedicamentos.core.estoque.usecase;

import fiap.msmedicamentos.core.estoque.entity.EstoqueMedicamento;
import fiap.msmedicamentos.core.estoque.exception.EstoqueInvalidoException;
import fiap.msmedicamentos.core.estoque.exception.EstoqueNaoEncontradoException;
import fiap.msmedicamentos.core.estoque.gateway.EstoqueGateway;
import fiap.msmedicamentos.core.estoque.service.EstoqueValidationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RemoverEstoqueUseCase {

    private final EstoqueGateway estoqueGateway;
    private final EstoqueValidationService validationService;

    public EstoqueMedicamento execute(Long medicamentoId, Long unidadeSaudeId, Integer quantidade) {
        log.info("Removendo {} unidades do medicamento {} na unidade {}", quantidade, medicamentoId, unidadeSaudeId);
        
        validationService.validarQuantidadePositiva(quantidade);
        validationService.validarExistenciaMedicamentoEUnidade(medicamentoId, unidadeSaudeId);

        var estoqueOpt = estoqueGateway.buscarPorMedicamentoEUnidade(medicamentoId, unidadeSaudeId);
        
        if (estoqueOpt.isEmpty()) {
            throw new EstoqueNaoEncontradoException("Estoque não encontrado para o medicamento na unidade especificada");
        }

        EstoqueMedicamento estoque = estoqueOpt.get();
        
        if (estoque.getQuantidade() < quantidade) {
            throw new EstoqueInvalidoException("Quantidade insuficiente em estoque. Disponível: " + estoque.getQuantidade());
        }

        estoque.removerQuantidade(quantidade);

        EstoqueMedicamento estoqueSalvo = estoqueGateway.salvar(estoque);
        log.info("Estoque atualizado para {} unidades", estoqueSalvo.getQuantidade());
        
        return estoqueSalvo;
    }
}
