package fiap.msmedicamentos.adapter.database.medicamento;

import fiap.msmedicamentos.core.medicamento.entity.Medicamento;
import org.springframework.stereotype.Component;

@Component
public class MedicamentoMapper {
    
    public MedicamentoEntity toEntity(Medicamento domain) {
        if (domain == null) return null;
        
        MedicamentoEntity entity = new MedicamentoEntity();
        entity.setId(domain.getId());
        entity.setNome(domain.getNome());
        entity.setPrincipioAtivo(domain.getPrincipioAtivo());
        entity.setFabricante(domain.getFabricante());
        entity.setDosagem(domain.getDosagem());
        
        return entity;
    }
    
    public Medicamento toDomain(MedicamentoEntity entity) {
        if (entity == null) return null;
        
        return new Medicamento(
            entity.getId(),
            entity.getNome(),
            entity.getPrincipioAtivo(),
            entity.getFabricante(),
            entity.getDosagem()
        );
    }
}
