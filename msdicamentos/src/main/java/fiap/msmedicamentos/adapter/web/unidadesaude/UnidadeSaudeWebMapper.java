package fiap.msmedicamentos.adapter.web.unidadesaude;

import fiap.msmedicamentos.core.unidadesaude.entity.UnidadeSaude;
import org.springframework.stereotype.Component;

@Component
public class UnidadeSaudeWebMapper {
    
    public UnidadeSaude toDomain(UnidadeSaudeRequest request) {
        if (request == null) return null;
        
        return new UnidadeSaude(
            null, // ID será gerado pelo banco
            request.getNome(),
            request.getEndereco(),
            request.getTelefone(),
            request.getEmail(),
            request.getAtiva() != null ? request.getAtiva() : true
        );
    }
    
    public UnidadeSaudeResponse toResponse(UnidadeSaude domain) {
        if (domain == null) return null;
        
        UnidadeSaudeResponse response = new UnidadeSaudeResponse();
        response.setId(domain.getId());
        response.setNome(domain.getNome());
        response.setEndereco(domain.getEndereco());
        response.setTelefone(domain.getTelefone());
        response.setEmail(domain.getEmail());
        response.setAtiva(domain.isAtiva());
        
        return response;
    }
}
