package fiap.msmedicamentos.core.medicamento.usecase;

import fiap.msmedicamentos.core.medicamento.exception.MedicamentoInvalidoException;
import fiap.msmedicamentos.core.medicamento.exception.MedicamentoNaoEncontradoException;
import fiap.msmedicamentos.core.medicamento.gateway.MedicamentoGateway;
import org.springframework.stereotype.Service;

@Service
public class DeletarMedicamentoUseCase {
    
    private final MedicamentoGateway gateway;
    
    public DeletarMedicamentoUseCase(MedicamentoGateway gateway) {
        this.gateway = gateway;
    }
    
    public void executar(Long id) {
        if (id == null) {
            throw new MedicamentoInvalidoException("ID do medicamento é obrigatório");
        }
        
        if (!gateway.existePorId(id)) {
            throw new MedicamentoNaoEncontradoException(id);
        }
        
        gateway.deletar(id);
    }
}
