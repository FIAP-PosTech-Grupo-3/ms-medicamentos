package fiap.msmedicamentos.adapter.database.unidadesaude;

import fiap.msmedicamentos.core.unidadesaude.entity.UnidadeSaude;
import org.springframework.stereotype.Component;

@Component
public class UnidadeSaudeMapper {
    
    public UnidadeSaudeEntity toEntity(UnidadeSaude domain) {
        if (domain == null) return null;
        
        UnidadeSaudeEntity entity = new UnidadeSaudeEntity();
        entity.setId(domain.getId());
        entity.setNome(domain.getNome());
        entity.setEndereco(domain.getEndereco());
        entity.setAtiva(domain.isAtiva());
        
        return entity;
    }
    
    public UnidadeSaude toDomain(UnidadeSaudeEntity entity) {
        if (entity == null) return null;
        
        return new UnidadeSaude(
            entity.getId(),
            entity.getNome(),
            entity.getEndereco(),
            entity.getAtiva() != null ? entity.getAtiva() : false
        );
    }
}
