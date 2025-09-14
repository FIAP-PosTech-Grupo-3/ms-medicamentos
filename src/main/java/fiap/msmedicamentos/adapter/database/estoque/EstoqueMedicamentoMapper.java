package fiap.msmedicamentos.adapter.database.estoque;

import fiap.msmedicamentos.core.estoque.entity.EstoqueMedicamento;
import org.springframework.stereotype.Component;

@Component
public class EstoqueMedicamentoMapper {

    public EstoqueMedicamentoEntity toEntity(EstoqueMedicamento domain) {
        if (domain == null) {
            return null;
        }
        
        EstoqueMedicamentoEntity entity = new EstoqueMedicamentoEntity();
        entity.setId(domain.getId());
        entity.setMedicamentoId(domain.getMedicamentoId());
        entity.setUnidadeSaudeId(domain.getUnidadeSaudeId());
        entity.setQuantidade(domain.getQuantidade());
        entity.setQuantidadeMinima(domain.getQuantidadeMinima());
        // O campo dataDeCriacao é gerenciado pelo @CreationTimestamp
        
        return entity;
    }

    public EstoqueMedicamento toDomain(EstoqueMedicamentoEntity entity) {
        if (entity == null) {
            return null;
        }
        
        EstoqueMedicamento domain = new EstoqueMedicamento();
        domain.setId(entity.getId());
        domain.setMedicamentoId(entity.getMedicamentoId());
        domain.setUnidadeSaudeId(entity.getUnidadeSaudeId());
        domain.setQuantidade(entity.getQuantidade());
        domain.setQuantidadeMinima(entity.getQuantidadeMinima());
        domain.setUltimaAtualizacao(entity.getUltimaAtualizacao());
        
        return domain;
    }
}
