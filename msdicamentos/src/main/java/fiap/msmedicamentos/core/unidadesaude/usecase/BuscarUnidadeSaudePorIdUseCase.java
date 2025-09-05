package fiap.msmedicamentos.core.unidadesaude.usecase;

import fiap.msmedicamentos.core.unidadesaude.entity.UnidadeSaude;
import fiap.msmedicamentos.core.unidadesaude.exception.UnidadeSaudeNaoEncontradaException;
import fiap.msmedicamentos.core.unidadesaude.gateway.UnidadeSaudeGateway;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class BuscarUnidadeSaudePorIdUseCase {

    private final UnidadeSaudeGateway unidadeSaudeGateway;

    public BuscarUnidadeSaudePorIdUseCase(UnidadeSaudeGateway unidadeSaudeGateway) {
        this.unidadeSaudeGateway = unidadeSaudeGateway;
    }

    public UnidadeSaude execute(Long id) {
        log.debug("Buscando unidade de saúde por ID: {}", id);
        
        return unidadeSaudeGateway.buscarPorId(id)
                .orElseThrow(() -> {
                    log.warn("Unidade de saúde não encontrada com ID: {}", id);
                    return new UnidadeSaudeNaoEncontradaException("Unidade de saúde não encontrada com ID: " + id);
                });
    }
}
