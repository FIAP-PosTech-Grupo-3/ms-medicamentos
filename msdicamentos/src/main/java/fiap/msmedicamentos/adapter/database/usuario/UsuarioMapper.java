package fiap.msmedicamentos.adapter.database.usuario;

import fiap.msmedicamentos.core.usuario.entity.Usuario;
import fiap.msmedicamentos.core.usuario.enums.PapelUsuario;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper {

    public UsuarioEntity toEntity(Usuario usuario) {
        if (usuario == null) {
            return null;
        }
        
        UsuarioEntity entity = new UsuarioEntity();
        entity.setId(usuario.getId());
        entity.setNome(usuario.getNome());
        entity.setEmail(usuario.getEmail());
        entity.setSenha(usuario.getSenha());
        entity.setPapel(toPapelEntity(usuario.getPapel()));
        entity.setAtivo(usuario.getAtivo());
        entity.setDataCriacao(usuario.getDataCriacao());
        
        return entity;
    }

    public Usuario toDomain(UsuarioEntity entity) {
        if (entity == null) {
            return null;
        }
        
        Usuario usuario = new Usuario();
        usuario.setId(entity.getId());
        usuario.setNome(entity.getNome());
        usuario.setEmail(entity.getEmail());
        usuario.setSenha(entity.getSenha());
        usuario.setPapel(toPapelDomain(entity.getPapel()));
        usuario.setAtivo(entity.getAtivo());
        usuario.setDataCriacao(entity.getDataCriacao());
        
        return usuario;
    }

    private PapelUsuarioEntity toPapelEntity(PapelUsuario papel) {
        if (papel == null) {
            return null;
        }
        return PapelUsuarioEntity.valueOf(papel.name());
    }

    private PapelUsuario toPapelDomain(PapelUsuarioEntity papel) {
        if (papel == null) {
            return null;
        }
        return PapelUsuario.valueOf(papel.name());
    }
}
