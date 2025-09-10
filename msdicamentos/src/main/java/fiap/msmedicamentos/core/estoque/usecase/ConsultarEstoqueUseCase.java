package fiap.msmedicamentos.core.estoque.usecase;

import fiap.msmedicamentos.core.estoque.entity.EstoqueMedicamento;
import fiap.msmedicamentos.core.estoque.gateway.EstoqueGateway;
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

    public Page<EstoqueMedicamento> buscarTodos(Pageable pageable) {
        log.info("Buscando todos os estoques com paginação: {}", pageable);
        return estoqueGateway.buscarTodos(pageable);
    }

    public List<EstoqueMedicamento> buscarPorMedicamento(Long medicamentoId) {
        log.info("Buscando estoque para medicamento ID: {}", medicamentoId);
        return estoqueGateway.buscarPorMedicamento(medicamentoId);
    }

    public List<EstoqueMedicamento> buscarPorUnidadeSaude(Long unidadeSaudeId) {
        log.info("Buscando estoque para unidade de saúde ID: {}", unidadeSaudeId);
        return estoqueGateway.buscarPorUnidadeSaude(unidadeSaudeId);
    }

    public List<EstoqueMedicamento> buscarEstoqueBaixo() {
        log.info("Buscando medicamentos com estoque baixo");
        return estoqueGateway.buscarComEstoqueBaixo();
    }
}
