package fiap.msmedicamentos.core.usuario.usecase;

import fiap.msmedicamentos.core.usuario.entity.Usuario;
import fiap.msmedicamentos.core.usuario.exception.UsuarioInvalidoException;
import fiap.msmedicamentos.core.usuario.exception.UsuarioJaExisteException;
import fiap.msmedicamentos.core.usuario.exception.UsuarioNaoEncontradoException;
import fiap.msmedicamentos.core.usuario.gateway.UsuarioGateway;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AtualizarUsuarioUseCase {

    private final UsuarioGateway usuarioGateway;

    public Usuario execute(Long id, Usuario usuarioAtualizado) {
        log.info("Iniciando atualização do usuário com ID: {}", id);
        
        if (!usuarioGateway.existePorId(id)) {
            throw new UsuarioNaoEncontradoException(id);
        }
        
        // Buscar usuário atual para preservar alguns dados
        Usuario usuarioAtual = usuarioGateway.buscarPorId(id).get();
        
        // Verificar se email foi alterado e se já existe
        if (!usuarioAtual.getEmail().equals(usuarioAtualizado.getEmail()) && 
            usuarioGateway.existePorEmail(usuarioAtualizado.getEmail())) {
            throw new UsuarioJaExisteException(usuarioAtualizado.getEmail());
        }
        
        usuarioAtualizado.setId(id);
        // Preservar campos não enviados no update
        usuarioAtualizado.setSenha(usuarioAtual.getSenha());
        usuarioAtualizado.setDataCriacao(usuarioAtual.getDataCriacao());
        
        if (!usuarioAtualizado.isValido()) {
            throw new UsuarioInvalidoException("Dados do usuário são inválidos");
        }
        
        Usuario usuarioSalvo = usuarioGateway.atualizar(usuarioAtualizado);
        log.info("Usuário atualizado com ID: {}", usuarioSalvo.getId());
        
        return usuarioSalvo;
    }
}
