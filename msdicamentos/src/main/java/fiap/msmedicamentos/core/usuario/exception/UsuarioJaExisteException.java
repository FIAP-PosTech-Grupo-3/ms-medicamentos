package fiap.msmedicamentos.core.usuario.exception;

public class UsuarioJaExisteException extends RuntimeException {
    public UsuarioJaExisteException(String email) {
        super("Usuário já existe com o email: " + email);
    }
}
