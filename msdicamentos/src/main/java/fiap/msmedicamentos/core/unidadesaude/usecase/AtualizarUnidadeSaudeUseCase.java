package fiap.msmedicamentos.core.unidadesaude.usecase;

import fiap.msmedicamentos.core.unidadesaude.entity.UnidadeSaude;
import fiap.msmedicamentos.core.unidadesaude.exception.UnidadeSaudeInvalidaException;
import fiap.msmedicamentos.core.unidadesaude.exception.UnidadeSaudeNaoEncontradaException;
import fiap.msmedicamentos.core.unidadesaude.gateway.UnidadeSaudeGateway;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AtualizarUnidadeSaudeUseCase {

    private final UnidadeSaudeGateway unidadeSaudeGateway;

    public UnidadeSaude execute(Long id, UnidadeSaude unidadeSaude) {
        log.info("Iniciando atualização da unidade de saúde com ID: {}", id);
        
        if (!unidadeSaudeGateway.existePorId(id)) {
            throw new UnidadeSaudeNaoEncontradaException("Unidade de saúde não encontrada com ID: " + id);
        }
        
        unidadeSaude.setId(id);
        
        if (!unidadeSaude.isValida()) {
            throw new UnidadeSaudeInvalidaException("Dados da unidade de saúde são inválidos");
        }
        
        UnidadeSaude unidadeSaudeAtualizada = unidadeSaudeGateway.atualizar(unidadeSaude);
        log.info("Unidade de saúde atualizada com ID: {}", unidadeSaudeAtualizada.getId());
        
        return unidadeSaudeAtualizada;
    }
}
