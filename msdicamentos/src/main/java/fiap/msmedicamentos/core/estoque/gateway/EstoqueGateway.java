package fiap.msmedicamentos.core.estoque.gateway;

import fiap.msmedicamentos.core.estoque.entity.EstoqueMedicamento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface EstoqueGateway {
    EstoqueMedicamento salvar(EstoqueMedicamento estoque);
    Optional<EstoqueMedicamento> buscarPorId(Long id);
    Optional<EstoqueMedicamento> buscarPorMedicamentoEUnidade(Long medicamentoId, Long unidadeSaudeId);
    List<EstoqueMedicamento> buscarPorMedicamento(Long medicamentoId);
    List<EstoqueMedicamento> buscarPorUnidadeSaude(Long unidadeSaudeId);
    Page<EstoqueMedicamento> buscarTodos(Pageable pageable);
    List<EstoqueMedicamento> buscarComEstoqueBaixo();
    EstoqueMedicamento atualizar(EstoqueMedicamento estoque);
    void deletar(Long id);
    boolean existePorId(Long id);
}
