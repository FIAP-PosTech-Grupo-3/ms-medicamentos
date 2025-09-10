package fiap.msmedicamentos.core.medicamento.usecase;

import fiap.msmedicamentos.core.estoque.gateway.EstoqueGateway;
import fiap.msmedicamentos.core.medicamento.exception.MedicamentoNaoEncontradoException;
import fiap.msmedicamentos.core.medicamento.gateway.MedicamentoGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeletarMedicamentoUseCase {
    
    private final MedicamentoGateway gateway;
    private final EstoqueGateway estoqueGateway;
    
    public void executar(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID do medicamento é obrigatório");
        }
        
        if (!gateway.existePorId(id)) {
            throw new MedicamentoNaoEncontradoException(id);
        }
        
        // Verificar se há estoque para este medicamento
        var estoques = estoqueGateway.buscarPorMedicamento(id);
        if (!estoques.isEmpty()) {
            throw new IllegalStateException("Não é possível excluir medicamento que possui estoque em unidades de saúde");
        }
        
        gateway.deletar(id);
    }
}
