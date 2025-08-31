package fiap.msmedicamentos.core.medicamento.usecase;

import fiap.msmedicamentos.core.medicamento.entity.Medicamento;
import fiap.msmedicamentos.core.medicamento.gateway.MedicamentoGateway;
import org.springframework.stereotype.Service;

@Service
public class CadastrarMedicamentoUseCase {

    private final MedicamentoGateway medicamentoGateway;

    public CadastrarMedicamentoUseCase(MedicamentoGateway medicamentoGateway) {
        this.medicamentoGateway = medicamentoGateway;
    }

    public Medicamento execute(Medicamento medicamento) {
        validar(medicamento);
        return medicamentoGateway.salvar(medicamento);
    }

    private void validar(Medicamento medicamento) {
        if (medicamento.getNome() == null || medicamento.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("Nome do medicamento é obrigatório");
        }
        if (medicamento.getTipo() == null) {
            throw new IllegalArgumentException("Tipo do medicamento é obrigatório");
        }
    }
}
