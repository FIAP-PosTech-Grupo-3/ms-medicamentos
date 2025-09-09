package fiap.msmedicamentos.core.usuario.usecase;

import fiap.msmedicamentos.core.usuario.exception.UsuarioNaoEncontradoException;
import fiap.msmedicamentos.core.usuario.gateway.UsuarioGateway;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class DeletarUsuarioUseCase {

    private final UsuarioGateway usuarioGateway;

    public void execute(Long id) {
        log.info("Iniciando deleção do usuário com ID: {}", id);
        
        if (!usuarioGateway.existePorId(id)) {
            throw new UsuarioNaoEncontradoException(id);
        }
        
        usuarioGateway.deletar(id);
        log.info("Usuário deletado com ID: {}", id);
    }
}
