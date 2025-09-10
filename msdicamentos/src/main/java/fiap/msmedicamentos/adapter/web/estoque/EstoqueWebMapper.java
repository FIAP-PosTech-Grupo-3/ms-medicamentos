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
        response.setPrecisaReposicao(estoque.precisaReposicao());
        response.setUltimaAtualizacao(estoque.getUltimaAtualizacao());
        
        // Se tiver objetos relacionados carregados
        if (estoque.getMedicamento() != null) {
            response.setNomeMedicamento(estoque.getMedicamento().getNome());
        }
        
        if (estoque.getUnidadeSaude() != null) {
            response.setNomeUnidadeSaude(estoque.getUnidadeSaude().getNome());
        }
        
        return response;
    }
}
