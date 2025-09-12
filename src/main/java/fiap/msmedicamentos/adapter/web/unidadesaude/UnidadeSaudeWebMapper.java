package fiap.msmedicamentos.adapter.web.unidadesaude;

import fiap.msmedicamentos.core.unidadesaude.entity.UnidadeSaude;
import org.springframework.stereotype.Component;

@Component
public class UnidadeSaudeWebMapper {
    
    public UnidadeSaude toDomain(UnidadeSaudeRequest request) {
        return new UnidadeSaude(
            null,
            request.getNome(),
            request.getEndereco(),
            true
        );
    }
    
    public UnidadeSaudeResponse toResponse(UnidadeSaude domain) {
        if (domain == null) return null;
        
        UnidadeSaudeResponse response = new UnidadeSaudeResponse();
        response.setId(domain.getId());
        response.setNome(domain.getNome());
        response.setEndereco(domain.getEndereco());
        response.setAtiva(domain.isAtiva());
        
        return response;
    }
}
