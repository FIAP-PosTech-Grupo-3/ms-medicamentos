package fiap.msmedicamentos.core.usuario.usecase;

import fiap.msmedicamentos.core.usuario.entity.Usuario;
import fiap.msmedicamentos.core.usuario.exception.UsuarioNaoEncontradoException;
import fiap.msmedicamentos.core.usuario.gateway.UsuarioGateway;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class BuscarUsuarioPorEmailUseCase {

    private final UsuarioGateway usuarioGateway;

    public Usuario execute(String email) {
        log.debug("Buscando usuário por email: {}", email);
        
        return usuarioGateway.buscarPorEmail(email)
                .orElseThrow(() -> new UsuarioNaoEncontradoException("Usuário não encontrado com email: " + email));
    }
}
