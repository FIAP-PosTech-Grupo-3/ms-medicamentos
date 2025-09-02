package fiap.msmedicamentos.core.medicamento.exception;

public class MedicamentoInvalidoException extends MedicamentoException {
    public MedicamentoInvalidoException(String campo) {
        super("Campo obrigatório: " + campo);
    }
}
