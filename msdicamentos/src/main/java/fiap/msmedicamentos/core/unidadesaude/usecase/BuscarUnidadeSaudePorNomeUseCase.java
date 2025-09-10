package fiap.msmedicamentos.core.unidadesaude.usecase;

import fiap.msmedicamentos.core.unidadesaude.entity.UnidadeSaude;
import fiap.msmedicamentos.core.unidadesaude.gateway.UnidadeSaudeGateway;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class BuscarUnidadeSaudePorNomeUseCase {

    private final UnidadeSaudeGateway unidadeSaudeGateway;

    public BuscarUnidadeSaudePorNomeUseCase(UnidadeSaudeGateway unidadeSaudeGateway) {
        this.unidadeSaudeGateway = unidadeSaudeGateway;
    }

    public Page<UnidadeSaude> execute(String nome, Pageable pageable) {
        log.debug("Buscando unidades de saúde por nome: {} - página: {}, tamanho: {}", 
                 nome, pageable.getPageNumber(), pageable.getPageSize());
        
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome é obrigatório");
        }
        
        return unidadeSaudeGateway.buscarPorNome(nome, pageable);
    }
}
