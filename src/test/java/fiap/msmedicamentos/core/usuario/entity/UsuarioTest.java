package fiap.msmedicamentos.core.usuario.entity;

import fiap.msmedicamentos.core.usuario.enums.PapelUsuario;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UsuarioTest {

    @Test
    void deveSerValidoComDadosCompletos() {
        // Arrange
        Usuario usuario = new Usuario(
            1L,
            "João Silva",
            "joao@email.com",
            "senha123",
            PapelUsuario.USUARIO,
            true,
            java.time.LocalDateTime.now()
        );

        // Act & Assert
        assertTrue(usuario.isValido());
    }

    @Test
    void deveSerInvalidoComNomeVazio() {
        // Arrange
        Usuario usuario = new Usuario(
            1L,
            "",
            "joao@email.com",
            "senha123",
            PapelUsuario.USUARIO,
            true,
            java.time.LocalDateTime.now()
        );

        // Act & Assert
        assertFalse(usuario.isValido());
    }

    @Test
    void deveSerInvalidoComNomeNulo() {
        // Arrange
        Usuario usuario = new Usuario(
            1L,
            null,
            "joao@email.com",
            "senha123",
            PapelUsuario.USUARIO,
            true,
            java.time.LocalDateTime.now()
        );

        // Act & Assert
        assertFalse(usuario.isValido());
    }

    @Test
    void deveSerInvalidoComEmailSemArroba() {
        // Arrange
        Usuario usuario = new Usuario(
            1L,
            "João Silva",
            "joaoemail.com",
            "senha123",
            PapelUsuario.USUARIO,
            true,
            java.time.LocalDateTime.now()
        );

        // Act & Assert
        assertFalse(usuario.isValido());
    }

    @Test
    void deveSerInvalidoComEmailSemPonto() {
        // Arrange
        Usuario usuario = new Usuario(
            1L,
            "João Silva",
            "joao@emailcom",
            "senha123",
            PapelUsuario.USUARIO,
            true,
            java.time.LocalDateTime.now()
        );

        // Act & Assert
        assertFalse(usuario.isValido());
    }

    @Test
    void deveSerInvalidoComEmailVazio() {
        // Arrange
        Usuario usuario = new Usuario(
            1L,
            "João Silva",
            "",
            "senha123",
            PapelUsuario.USUARIO,
            true,
            java.time.LocalDateTime.now()
        );

        // Act & Assert
        assertFalse(usuario.isValido());
    }

    @Test
    void deveSerInvalidoComPapelNulo() {
        // Arrange
        Usuario usuario = new Usuario(
            1L,
            "João Silva",
            "joao@email.com",
            "senha123",
            null,
            true,
            java.time.LocalDateTime.now()
        );

        // Act & Assert
        assertFalse(usuario.isValido());
    }

    @Test
    void deveRetornarTrueParaUsuarioAdmin() {
        // Arrange
        Usuario admin = new Usuario(
            1L,
            "Admin",
            "admin@email.com",
            "senha123",
            PapelUsuario.ADMIN,
            true,
            java.time.LocalDateTime.now()
        );

        // Act & Assert
        assertTrue(admin.isAdmin());
        assertTrue(admin.podeCadastrarMedicamento());
        assertTrue(admin.podeGerenciarUnidadeSaude());
        assertTrue(admin.podeGerenciarUsuarios());
    }

    @Test
    void deveRetornarFalseParaUsuarioComum() {
        // Arrange
        Usuario usuario = new Usuario(
            1L,
            "João Silva",
            "joao@email.com",
            "senha123",
            PapelUsuario.USUARIO,
            true,
            java.time.LocalDateTime.now()
        );

        // Act & Assert
        assertFalse(usuario.isAdmin());
        assertFalse(usuario.podeCadastrarMedicamento());
        assertFalse(usuario.podeGerenciarUnidadeSaude());
        assertFalse(usuario.podeGerenciarUsuarios());
    }

    @Test
    void deveAtivarUsuario() {
        // Arrange
        Usuario usuario = new Usuario(
            1L,
            "João Silva",
            "joao@email.com",
            "senha123",
            PapelUsuario.USUARIO,
            false,
            java.time.LocalDateTime.now()
        );

        // Act
        usuario.ativar();

        // Assert
        assertTrue(usuario.isAtivo());
    }

    @Test
    void deveDesativarUsuario() {
        // Arrange
        Usuario usuario = new Usuario(
            1L,
            "João Silva",
            "joao@email.com",
            "senha123",
            PapelUsuario.USUARIO,
            true,
            java.time.LocalDateTime.now()
        );

        // Act
        usuario.desativar();

        // Assert
        assertFalse(usuario.isAtivo());
    }
}
