package fiap.msmedicamentos.core.medicamento.usecase;

import fiap.msmedicamentos.core.medicamento.entity.Medicamento;
import fiap.msmedicamentos.core.medicamento.gateway.MedicamentoGateway;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class BuscarTodosMedicamentosUseCase {

    private final MedicamentoGateway medicamentoGateway;

    public BuscarTodosMedicamentosUseCase(MedicamentoGateway medicamentoGateway) {
        this.medicamentoGateway = medicamentoGateway;
    }

    public Page<Medicamento> execute(Pageable pageable) {
        return medicamentoGateway.buscarTodos(pageable);
    }
}
