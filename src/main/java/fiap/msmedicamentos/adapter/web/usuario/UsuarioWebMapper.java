package fiap.msmedicamentos.adapter.web.usuario;

import fiap.msmedicamentos.core.usuario.entity.Usuario;
import org.springframework.stereotype.Component;

@Component
public class UsuarioWebMapper {

    public Usuario toDomain(CadastrarUsuarioRequest request) {
        if (request == null) {
            return null;
        }
        
        Usuario usuario = new Usuario();
        usuario.setNome(request.getNome());
        usuario.setEmail(request.getEmail());
        usuario.setSenha(request.getSenha());
        usuario.setPapel(request.getPapel());
        usuario.setAtivo(request.getAtivo());
        
        return usuario;
    }

    public Usuario toDomain(AtualizarUsuarioRequest request) {
        if (request == null) {
            return null;
        }
        
        Usuario usuario = new Usuario();
        usuario.setNome(request.getNome());
        usuario.setEmail(request.getEmail());
        usuario.setPapel(request.getPapel());
        usuario.setAtivo(request.getAtivo());
        
        return usuario;
    }

    public UsuarioResponse toResponse(Usuario usuario) {
        if (usuario == null) {
            return null;
        }
        
        UsuarioResponse response = new UsuarioResponse();
        response.setId(usuario.getId());
        response.setNome(usuario.getNome());
        response.setEmail(usuario.getEmail());
        response.setPapel(usuario.getPapel());
        response.setAtivo(usuario.getAtivo());
        response.setDataCriacao(usuario.getDataCriacao());
        
        return response;
    }
}
