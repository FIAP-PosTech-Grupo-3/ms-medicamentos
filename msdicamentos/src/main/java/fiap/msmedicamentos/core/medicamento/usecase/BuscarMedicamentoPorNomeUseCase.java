package fiap.msmedicamentos.core.medicamento.usecase;

import fiap.msmedicamentos.core.medicamento.entity.Medicamento;
import fiap.msmedicamentos.core.medicamento.gateway.MedicamentoGateway;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class BuscarMedicamentoPorNomeUseCase {

    private final MedicamentoGateway medicamentoGateway;

    public BuscarMedicamentoPorNomeUseCase(MedicamentoGateway medicamentoGateway) {
        this.medicamentoGateway = medicamentoGateway;
    }

    public Page<Medicamento> execute(String nome, Pageable pageable) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome é obrigatório");
        }
        
        return medicamentoGateway.buscarPorNome(nome, pageable);
    }
}
