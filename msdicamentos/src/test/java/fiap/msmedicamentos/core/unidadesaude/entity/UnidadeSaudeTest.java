package fiap.msmedicamentos.core.unidadesaude.entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UnidadeSaudeTest {

    @Test
    void deveSerValidaComNomeEEndereco() {
        // Arrange
        UnidadeSaude unidadeSaude = new UnidadeSaude(
            1L,
            "UBS Vila Esperança",
            "Rua das Flores, 123 - Vila Esperança - São Paulo/SP",
            true
        );

        // Act & Assert
        assertTrue(unidadeSaude.isValida());
    }

    @Test
    void deveSerInvalidaComNomeVazio() {
        // Arrange
        UnidadeSaude unidadeSaude = new UnidadeSaude(
            1L,
            "",
            "Rua das Flores, 123",
            true
        );

        // Act & Assert
        assertFalse(unidadeSaude.isValida());
    }

    @Test
    void deveSerInvalidaComNomeNulo() {
        // Arrange
        UnidadeSaude unidadeSaude = new UnidadeSaude(
            1L,
            null,
            "Rua das Flores, 123",
            true
        );

        // Act & Assert
        assertFalse(unidadeSaude.isValida());
    }

    @Test
    void deveSerInvalidaComEnderecoVazio() {
        // Arrange
        UnidadeSaude unidadeSaude = new UnidadeSaude(
            1L,
            "UBS Vila Esperança",
            "",
            true
        );

        // Act & Assert
        assertFalse(unidadeSaude.isValida());
    }

    @Test
    void deveSerInvalidaComEnderecoNulo() {
        // Arrange
        UnidadeSaude unidadeSaude = new UnidadeSaude(
            1L,
            "UBS Vila Esperança",
            null,
            true
        );

        // Act & Assert
        assertFalse(unidadeSaude.isValida());
    }

    @Test
    void deveRetornarTrueQuandoAtiva() {
        // Arrange
        UnidadeSaude unidadeSaude = new UnidadeSaude(
            1L,
            "UBS Vila Esperança",
            "Rua das Flores, 123",
            true
        );

        // Act & Assert
        assertTrue(unidadeSaude.isAtiva());
    }

    @Test
    void deveRetornarFalseQuandoInativa() {
        // Arrange
        UnidadeSaude unidadeSaude = new UnidadeSaude(
            1L,
            "UBS Vila Esperança",
            "Rua das Flores, 123",
            false
        );

        // Act & Assert
        assertFalse(unidadeSaude.isAtiva());
    }
}
