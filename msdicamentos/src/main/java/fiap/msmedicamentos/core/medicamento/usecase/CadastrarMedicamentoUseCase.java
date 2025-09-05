package fiap.msmedicamentos.core.medicamento.usecase;

import fiap.msmedicamentos.core.medicamento.entity.Medicamento;
import fiap.msmedicamentos.core.medicamento.exception.MedicamentoInvalidoException;
import fiap.msmedicamentos.core.medicamento.gateway.MedicamentoGateway;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CadastrarMedicamentoUseCase {

    private final MedicamentoGateway medicamentoGateway;

    public Medicamento execute(Medicamento medicamento) {
        log.debug("Validando medicamento: {}", medicamento.getNome());
        validar(medicamento);
        
        Medicamento medicamentoSalvo = medicamentoGateway.salvar(medicamento);
        log.info("Medicamento salvo com ID: {}", medicamentoSalvo.getId());
        
        return medicamentoSalvo;
    }

    private void validar(Medicamento medicamento) {
        if (!medicamento.isValido()) {
            throw new MedicamentoInvalidoException("Dados do medicamento são inválidos");
        }
    }
}
