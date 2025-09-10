package fiap.msmedicamentos.core.usuario.usecase;

import fiap.msmedicamentos.core.usuario.entity.Usuario;
import fiap.msmedicamentos.core.usuario.exception.UsuarioInvalidoException;
import fiap.msmedicamentos.core.usuario.exception.UsuarioJaExisteException;
import fiap.msmedicamentos.core.usuario.gateway.UsuarioGateway;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CadastrarUsuarioUseCase {

    private final UsuarioGateway usuarioGateway;
    private final PasswordEncoder passwordEncoder;

    public Usuario execute(Usuario usuario) {
        log.info("Iniciando cadastro de novo usuário: {}", usuario.getEmail());
        
        if (!usuario.isValido()) {
            throw new UsuarioInvalidoException("Dados do usuário são inválidos");
        }
        
        if (usuarioGateway.existePorEmail(usuario.getEmail())) {
            throw new UsuarioJaExisteException(usuario.getEmail());
        }
        
        // Criptografar a senha antes de salvar
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        
        // Configurações padrão para novo usuário
        usuario.setAtivo(true);
        
        Usuario usuarioSalvo = usuarioGateway.salvar(usuario);
        log.info("Usuário cadastrado com ID: {}", usuarioSalvo.getId());
        
        return usuarioSalvo;
    }
}
