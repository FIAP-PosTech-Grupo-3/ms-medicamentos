package fiap.msmedicamentos.core.usuario.gateway;

import fiap.msmedicamentos.core.usuario.entity.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface UsuarioGateway {
    
    Usuario salvar(Usuario usuario);
    
    Optional<Usuario> buscarPorId(Long id);
    
    Optional<Usuario> buscarPorEmail(String email);
    
    Page<Usuario> buscarTodos(Pageable pageable);
    
    Page<Usuario> buscarPorNome(String nome, Pageable pageable);
    
    Page<Usuario> buscarAtivos(Pageable pageable);
    
    Usuario atualizar(Usuario usuario);
    
    void deletar(Long id);
    
    boolean existePorId(Long id);
    
    boolean existePorEmail(String email);
    
    long contarTotal();
    
    long contarAtivos();
}
