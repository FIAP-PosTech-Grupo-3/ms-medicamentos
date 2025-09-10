package fiap.msmedicamentos.core.estoque.usecase;

import fiap.msmedicamentos.core.estoque.entity.EstoqueMedicamento;
import fiap.msmedicamentos.core.estoque.gateway.EstoqueGateway;
import fiap.msmedicamentos.core.medicamento.gateway.MedicamentoGateway;
import fiap.msmedicamentos.core.unidadesaude.gateway.UnidadeSaudeGateway;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConsultarEstoqueUseCase {

    private final EstoqueGateway estoqueGateway;
    private final MedicamentoGateway medicamentoGateway;
    private final UnidadeSaudeGateway unidadeSaudeGateway;

    public Page<EstoqueMedicamento> buscarTodos(Pageable pageable) {
        log.info("Buscando todos os estoques com paginação: {}", pageable);
        Page<EstoqueMedicamento> estoques = estoqueGateway.buscarTodos(pageable);
        return estoques.map(this::carregarObjetosRelacionados);
    }

    public List<EstoqueMedicamento> buscarPorMedicamento(Long medicamentoId) {
        log.info("Buscando estoque para medicamento ID: {}", medicamentoId);
        List<EstoqueMedicamento> estoques = estoqueGateway.buscarPorMedicamento(medicamentoId);
        return estoques.stream().map(this::carregarObjetosRelacionados).toList();
    }

    public List<EstoqueMedicamento> buscarPorUnidadeSaude(Long unidadeSaudeId) {
        log.info("Buscando estoque para unidade de saúde ID: {}", unidadeSaudeId);
        List<EstoqueMedicamento> estoques = estoqueGateway.buscarPorUnidadeSaude(unidadeSaudeId);
        return estoques.stream().map(this::carregarObjetosRelacionados).toList();
    }

    public List<EstoqueMedicamento> buscarEstoqueBaixo() {
        log.info("Buscando medicamentos com estoque baixo");
        List<EstoqueMedicamento> estoques = estoqueGateway.buscarComEstoqueBaixo();
        return estoques.stream().map(this::carregarObjetosRelacionados).toList();
    }

    private EstoqueMedicamento carregarObjetosRelacionados(EstoqueMedicamento estoque) {
        // Carregar medicamento se não estiver carregado
        if (estoque.getMedicamento() == null && estoque.getMedicamentoId() != null) {
            medicamentoGateway.buscarPorId(estoque.getMedicamentoId())
                    .ifPresent(estoque::setMedicamento);
        }
        
        // Carregar unidade de saúde se não estiver carregada
        if (estoque.getUnidadeSaude() == null && estoque.getUnidadeSaudeId() != null) {
            unidadeSaudeGateway.buscarPorId(estoque.getUnidadeSaudeId())
                    .ifPresent(estoque::setUnidadeSaude);
        }
        
        return estoque;
    }
}
