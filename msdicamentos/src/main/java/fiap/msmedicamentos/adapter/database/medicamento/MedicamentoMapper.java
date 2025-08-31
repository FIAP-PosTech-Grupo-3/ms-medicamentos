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
        entity.setTipo(domain.getTipo());
        entity.setFabricante(domain.getFabricante());
        entity.setDataValidade(domain.getDataValidade());
        entity.setDosagem(domain.getDosagem());
        entity.setFormaFarmaceutica(domain.getFormaFarmaceutica());
        entity.setPrincipioAtivo(domain.getPrincipioAtivo());
        entity.setLote(domain.getLote());
        entity.setDataFabricacao(domain.getDataFabricacao());
        entity.setQuantidadeEstoque(domain.getQuantidadeEstoque());
        entity.setPrecisaReceita(domain.isPrecisaReceita());
        entity.setEmFalta(domain.isEmFalta());
        
        return entity;
    }
    
    public Medicamento toDomain(MedicamentoEntity entity) {
        if (entity == null) return null;
        
        return new Medicamento(
            entity.getId(),
            entity.getNome(),
            entity.getTipo(),
            entity.getFabricante(),
            entity.getDataValidade(),
            entity.getDosagem(),
            entity.getFormaFarmaceutica(),
            entity.getPrincipioAtivo(),
            entity.getLote(),
            entity.getDataFabricacao(),
            entity.getQuantidadeEstoque(),
            entity.getPrecisaReceita() != null ? entity.getPrecisaReceita() : false,
            entity.getEmFalta() != null ? entity.getEmFalta() : false
        );
    }
}
