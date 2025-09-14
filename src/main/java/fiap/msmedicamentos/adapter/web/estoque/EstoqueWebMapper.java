package fiap.msmedicamentos.adapter.web.estoque;

import fiap.msmedicamentos.core.estoque.entity.EstoqueMedicamento;
import org.springframework.stereotype.Component;

@Component
public class EstoqueWebMapper {

    public EstoqueResponse toResponse(EstoqueMedicamento estoque) {
        if (estoque == null) {
            return null;
        }
        
        EstoqueResponse response = new EstoqueResponse();
        response.setId(estoque.getId());
        response.setMedicamentoId(estoque.getMedicamentoId());
        response.setUnidadeSaudeId(estoque.getUnidadeSaudeId());
        response.setQuantidade(estoque.getQuantidade());
        response.setQuantidadeMinima(estoque.getQuantidadeMinima());
        response.setUltimaAtualizacao(estoque.getUltimaAtualizacao());
        
        return response;
    }
}
