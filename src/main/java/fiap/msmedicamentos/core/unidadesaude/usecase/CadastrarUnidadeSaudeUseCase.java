package fiap.msmedicamentos.core.unidadesaude.usecase;

import fiap.msmedicamentos.core.unidadesaude.entity.UnidadeSaude;
import fiap.msmedicamentos.core.unidadesaude.exception.UnidadeSaudeInvalidaException;
import fiap.msmedicamentos.core.unidadesaude.gateway.UnidadeSaudeGateway;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CadastrarUnidadeSaudeUseCase {

    private final UnidadeSaudeGateway unidadeSaudeGateway;

    public UnidadeSaude execute(UnidadeSaude unidadeSaude) {
        log.info("Iniciando cadastro de nova unidade de saúde");
        
        if (!unidadeSaude.isValida()) {
            throw new UnidadeSaudeInvalidaException("Dados da unidade de saúde são inválidos");
        }
        
        UnidadeSaude unidadeSaudeSalva = unidadeSaudeGateway.salvar(unidadeSaude);
        log.info("Unidade de saúde cadastrada com ID: {}", unidadeSaudeSalva.getId());
        
        return unidadeSaudeSalva;
    }
}
