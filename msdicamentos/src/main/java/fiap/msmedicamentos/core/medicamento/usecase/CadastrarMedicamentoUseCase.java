package fiap.msmedicamentos.core.medicamento.usecase;

import fiap.msmedicamentos.core.medicamento.entity.Medicamento;
import fiap.msmedicamentos.core.medicamento.exception.MedicamentoInvalidoException;
import fiap.msmedicamentos.core.medicamento.gateway.MedicamentoGateway;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CadastrarMedicamentoUseCase {

    private final MedicamentoGateway medicamentoGateway;

    public CadastrarMedicamentoUseCase(MedicamentoGateway medicamentoGateway) {
        this.medicamentoGateway = medicamentoGateway;
    }

    public Medicamento execute(Medicamento medicamento) {
        log.debug("Validando medicamento: {}", medicamento.getNome());
        validar(medicamento);
        
        Medicamento medicamentoSalvo = medicamentoGateway.salvar(medicamento);
        log.info("Medicamento salvo com ID: {}", medicamentoSalvo.getId());
        
        return medicamentoSalvo;
    }

    private void validar(Medicamento medicamento) {
        if (medicamento.getNome() == null || medicamento.getNome().trim().isEmpty()) {
            log.warn("Erro de validação: nome inválido");
            throw new MedicamentoInvalidoException("nome");
        }
        if (medicamento.getTipo() == null) {
            log.warn("Erro de validação: tipo inválido");
            throw new MedicamentoInvalidoException("tipo");
        }
    }
}
