package fiap.msmedicamentos.core.usuario.usecase;

import fiap.msmedicamentos.core.usuario.entity.Usuario;
import fiap.msmedicamentos.core.usuario.gateway.UsuarioGateway;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class BuscarTodosUsuariosUseCase {

    private final UsuarioGateway usuarioGateway;

    public Page<Usuario> execute(Pageable pageable) {
        log.debug("Buscando todos os usuários - página: {}, tamanho: {}", 
                  pageable.getPageNumber(), pageable.getPageSize());
        
        return usuarioGateway.buscarTodos(pageable);
    }

    public Page<Usuario> executePorNome(String nome, Pageable pageable) {
        log.debug("Buscando usuários por nome: {} - página: {}, tamanho: {}", 
                  nome, pageable.getPageNumber(), pageable.getPageSize());
        
        return usuarioGateway.buscarPorNome(nome, pageable);
    }

    public Page<Usuario> executeAtivos(Pageable pageable) {
        log.debug("Buscando usuários ativos - página: {}, tamanho: {}", 
                  pageable.getPageNumber(), pageable.getPageSize());
        
        return usuarioGateway.buscarAtivos(pageable);
    }
}
