package fiap.msmedicamentos.core.medicamento.usecase;

import fiap.msmedicamentos.core.medicamento.entity.Medicamento;
import fiap.msmedicamentos.core.medicamento.exception.MedicamentoNaoEncontradoException;
import fiap.msmedicamentos.core.medicamento.gateway.MedicamentoGateway;
import org.springframework.stereotype.Service;

@Service
public class BuscarMedicamentoPorIdUseCase {

    private final MedicamentoGateway medicamentoGateway;

    public BuscarMedicamentoPorIdUseCase(MedicamentoGateway medicamentoGateway) {
        this.medicamentoGateway = medicamentoGateway;
    }

    public Medicamento execute(Long id) {
        if (id == null) {
            throw new MedicamentoNaoEncontradoException(null);
        }
        
        return medicamentoGateway.buscarPorId(id)
                .orElseThrow(() -> new MedicamentoNaoEncontradoException(id));
    }
}
