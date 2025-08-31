package fiap.msmedicamentos.core.medicamento.exception;

public class MedicamentoNaoEncontradoException extends MedicamentoException {
    public MedicamentoNaoEncontradoException(Long id) {
        super("Medicamento não encontrado com ID: " + id);
    }
}
