package fiap.msmedicamentos.core.unidadesaude.usecase;

import fiap.msmedicamentos.core.unidadesaude.entity.UnidadeSaude;
import fiap.msmedicamentos.core.unidadesaude.gateway.UnidadeSaudeGateway;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class BuscarTodasUnidadesSaudeUseCase {

    private final UnidadeSaudeGateway unidadeSaudeGateway;

    public BuscarTodasUnidadesSaudeUseCase(UnidadeSaudeGateway unidadeSaudeGateway) {
        this.unidadeSaudeGateway = unidadeSaudeGateway;
    }

    public Page<UnidadeSaude> execute(Pageable pageable) {
        log.debug("Buscando todas as unidades de saúde - página: {}, tamanho: {}", 
                 pageable.getPageNumber(), pageable.getPageSize());
        
        return unidadeSaudeGateway.buscarTodas(pageable);
    }
}
