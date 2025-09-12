package fiap.msmedicamentos.adapter.database.usuario;

import fiap.msmedicamentos.core.usuario.entity.Usuario;
import fiap.msmedicamentos.core.usuario.enums.PapelUsuario;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UsuarioMapperTest {

    private final UsuarioMapper mapper = new UsuarioMapper();

    @Test
    void deveConverterDomainParaEntity() {
        // Arrange
        Usuario domain = new Usuario();
        domain.setId(1L);
        domain.setNome("João Silva");
        domain.setEmail("joao@email.com");
        domain.setSenha("senha123");
        domain.setPapel(PapelUsuario.ADMIN);
        domain.setAtivo(true);

        // Act
        UsuarioEntity entity = mapper.toEntity(domain);

        // Assert
        assertEquals(1L, entity.getId());
        assertEquals("João Silva", entity.getNome());
        assertEquals("joao@email.com", entity.getEmail());
        assertEquals("senha123", entity.getSenha());
        assertEquals(PapelUsuarioEntity.ADMIN, entity.getPapel());
        assertTrue(entity.getAtivo());
    }

    @Test
    void deveConverterEntityParaDomain() {
        // Arrange
        UsuarioEntity entity = new UsuarioEntity();
        entity.setId(2L);
        entity.setNome("Maria Santos");
        entity.setEmail("maria@email.com");
        entity.setSenha("senha456");
        entity.setPapel(PapelUsuarioEntity.USUARIO);
        entity.setAtivo(false);

        // Act
        Usuario domain = mapper.toDomain(entity);

        // Assert
        assertEquals(2L, domain.getId());
        assertEquals("Maria Santos", domain.getNome());
        assertEquals("maria@email.com", domain.getEmail());
        assertEquals("senha456", domain.getSenha());
        assertEquals(PapelUsuario.USUARIO, domain.getPapel());
        assertFalse(domain.getAtivo());
    }
}
