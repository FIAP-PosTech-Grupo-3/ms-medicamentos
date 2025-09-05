package fiap.msmedicamentos.core.medicamento.usecase;

import fiap.msmedicamentos.core.medicamento.entity.Medicamento;
import fiap.msmedicamentos.core.medicamento.exception.MedicamentoInvalidoException;
import fiap.msmedicamentos.core.medicamento.exception.MedicamentoNaoEncontradoException;
import fiap.msmedicamentos.core.medicamento.gateway.MedicamentoGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AtualizarMedicamentoUseCase {
    
    private final MedicamentoGateway gateway;
    
    public Medicamento executar(Long id, Medicamento medicamento) {
        if (id == null) {
            throw new MedicamentoInvalidoException("ID do medicamento é obrigatório");
        }
        
        if (!gateway.existePorId(id)) {
            throw new MedicamentoNaoEncontradoException(id);
        }
        
        medicamento.setId(id);
        validar(medicamento);
        
        return gateway.atualizar(medicamento);
    }
    
    private void validar(Medicamento medicamento) {
        if (!medicamento.isValido()) {
            throw new MedicamentoInvalidoException("Dados do medicamento são inválidos");
        }
    }
}
