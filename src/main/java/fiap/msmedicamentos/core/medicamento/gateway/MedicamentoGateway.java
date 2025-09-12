package fiap.msmedicamentos.core.medicamento.gateway;

import fiap.msmedicamentos.core.medicamento.entity.Medicamento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface MedicamentoGateway {
    Medicamento salvar(Medicamento medicamento);
    Optional<Medicamento> buscarPorId(Long id);
    Page<Medicamento> buscarTodos(Pageable pageable);
    Page<Medicamento> buscarPorNome(String nome, Pageable pageable);
    Medicamento atualizar(Medicamento medicamento);
    void deletar(Long id);
    boolean existePorId(Long id);
}
