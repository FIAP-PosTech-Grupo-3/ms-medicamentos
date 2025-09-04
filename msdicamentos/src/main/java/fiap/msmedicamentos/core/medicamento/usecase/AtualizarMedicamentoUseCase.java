package fiap.msmedicamentos.core.medicamento.usecase;

import fiap.msmedicamentos.core.medicamento.entity.Medicamento;
import fiap.msmedicamentos.core.medicamento.exception.MedicamentoInvalidoException;
import fiap.msmedicamentos.core.medicamento.exception.MedicamentoNaoEncontradoException;
import fiap.msmedicamentos.core.medicamento.gateway.MedicamentoGateway;
import org.springframework.stereotype.Service;

@Service
public class AtualizarMedicamentoUseCase {
    
    private final MedicamentoGateway gateway;
    
    public AtualizarMedicamentoUseCase(MedicamentoGateway gateway) {
        this.gateway = gateway;
    }
    
    public Medicamento executar(Long id, Medicamento medicamento) {
        if (id == null) {
            throw new MedicamentoInvalidoException("ID do medicamento é obrigatório");
        }
        
        if (!gateway.existePorId(id)) {
            throw new MedicamentoNaoEncontradoException(id);
        }
        
        if (!medicamento.isValido()) {
            throw new MedicamentoInvalidoException("Dados do medicamento são inválidos");
        }
        
        medicamento = new Medicamento(
            id,
            medicamento.getNome(),
            medicamento.getTipo(),
            medicamento.getFabricante(),
            medicamento.getDataValidade(),
            medicamento.getDosagem(),
            medicamento.getFormaFarmaceutica(),
            medicamento.getPrincipioAtivo(),
            medicamento.getLote(),
            medicamento.getDataFabricacao(),
            medicamento.isPrecisaReceita()
        );
        
        return gateway.atualizar(medicamento);
    }
}
