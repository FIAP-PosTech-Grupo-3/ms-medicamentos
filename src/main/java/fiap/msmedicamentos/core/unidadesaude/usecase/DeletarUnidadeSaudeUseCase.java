package fiap.msmedicamentos.core.unidadesaude.usecase;

import fiap.msmedicamentos.core.estoque.gateway.EstoqueGateway;
import fiap.msmedicamentos.core.unidadesaude.exception.UnidadeSaudeNaoEncontradaException;
import fiap.msmedicamentos.core.unidadesaude.gateway.UnidadeSaudeGateway;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class DeletarUnidadeSaudeUseCase {

    private final UnidadeSaudeGateway unidadeSaudeGateway;
    private final EstoqueGateway estoqueGateway;

    public void execute(Long id) {
        log.debug("Deletando unidade de saúde ID: {}", id);
        
        if (id == null) {
            throw new IllegalArgumentException("ID da unidade de saúde é obrigatório");
        }
        
        if (!unidadeSaudeGateway.existePorId(id)) {
            throw new UnidadeSaudeNaoEncontradaException("Unidade de saúde não encontrada com ID: " + id);
        }
        
        // Verificar se há estoque para esta unidade de saúde
        var estoques = estoqueGateway.buscarPorUnidadeSaude(id);
        if (!estoques.isEmpty()) {
            throw new IllegalStateException("Não é possível excluir unidade de saúde que possui medicamentos em estoque");
        }
        
        unidadeSaudeGateway.deletar(id);
        log.info("Unidade de saúde deletada com ID: {}", id);
    }
}
