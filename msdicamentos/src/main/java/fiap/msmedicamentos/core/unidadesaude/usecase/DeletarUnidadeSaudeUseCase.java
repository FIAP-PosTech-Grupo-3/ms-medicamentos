package fiap.msmedicamentos.core.unidadesaude.usecase;

import fiap.msmedicamentos.core.unidadesaude.exception.UnidadeSaudeNaoEncontradaException;
import fiap.msmedicamentos.core.unidadesaude.gateway.UnidadeSaudeGateway;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class DeletarUnidadeSaudeUseCase {

    private final UnidadeSaudeGateway unidadeSaudeGateway;

    public DeletarUnidadeSaudeUseCase(UnidadeSaudeGateway unidadeSaudeGateway) {
        this.unidadeSaudeGateway = unidadeSaudeGateway;
    }

    public void execute(Long id) {
        log.debug("Deletando unidade de saúde ID: {}", id);
        
        if (!unidadeSaudeGateway.existePorId(id)) {
            throw new UnidadeSaudeNaoEncontradaException("Unidade de saúde não encontrada com ID: " + id);
        }
        
        unidadeSaudeGateway.deletar(id);
        log.info("Unidade de saúde deletada com ID: {}", id);
    }
}
