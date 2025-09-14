package fiap.msmedicamentos.core.estoque.entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EstoqueMedicamentoTest {

    @Test
    void deveSerValidoComDadosCompletos() {
        // Arrange
        EstoqueMedicamento estoque = new EstoqueMedicamento(
            1L,
            1L,  // medicamentoId
            1L,  // unidadeSaudeId
            100, // quantidade
            10,   // quantidadeMinima
            null
        );

        // Act & Assert
        assertTrue(estoque.isValido());
    }

    @Test
    void deveSerInvalidoComMedicamentoIdNulo() {
        // Arrange
        EstoqueMedicamento estoque = new EstoqueMedicamento(
            1L,
            null,
            1L,
            100,
            10,
            null
        );

        // Act & Assert
        assertFalse(estoque.isValido());
    }

    @Test
    void deveSerInvalidoComUnidadeSaudeIdNulo() {
        // Arrange
        EstoqueMedicamento estoque = new EstoqueMedicamento(
            1L,
            1L,
            null,
            100,
            10,
            null
        );

        // Act & Assert
        assertFalse(estoque.isValido());
    }

    @Test
    void deveSerInvalidoComQuantidadeNula() {
        // Arrange
        EstoqueMedicamento estoque = new EstoqueMedicamento(
            1L,
            1L,
            1L,
            null,
            10,
            null
        );

        // Act & Assert
        assertFalse(estoque.isValido());
    }

    @Test
    void deveSerInvalidoComQuantidadeNegativa() {
        // Arrange
        EstoqueMedicamento estoque = new EstoqueMedicamento(
            1L,
            1L,
            1L,
            -5,
            10,
            null
        );

        // Act & Assert
        assertFalse(estoque.isValido());
    }

    @Test
    void deveRetornarTrueQuandoTemEstoque() {
        // Arrange
        EstoqueMedicamento estoque = new EstoqueMedicamento(
            1L,
            1L,
            1L,
            50,
            10,
            null
        );

        // Act & Assert
        assertTrue(estoque.temEstoque());
    }

    @Test
    void deveRetornarFalseQuandoNaoTemEstoque() {
        // Arrange
        EstoqueMedicamento estoque = new EstoqueMedicamento(
            1L,
            1L,
            1L,
            0,
            10,
            null
        );

        // Act & Assert
        assertFalse(estoque.temEstoque());
    }

    @Test
    void deveRetornarTrueQuandoPrecisaReposicao() {
        // Arrange
        EstoqueMedicamento estoque = new EstoqueMedicamento(
            1L,
            1L,
            1L,
            5,  // quantidade menor que mínima
            10,  // quantidade mínima
            null
        );

        // Act & Assert
        assertTrue(estoque.precisaReposicao());
    }

    @Test
    void deveRetornarFalseQuandoNaoPrecisaReposicao() {
        // Arrange
        EstoqueMedicamento estoque = new EstoqueMedicamento(
            1L,
            1L,
            1L,
            20, // quantidade maior que mínima
            10,  // quantidade mínima
            null
        );

        // Act & Assert
        assertFalse(estoque.precisaReposicao());
    }

    @Test
    void deveAdicionarQuantidade() {
        // Arrange
        EstoqueMedicamento estoque = new EstoqueMedicamento(
            1L, 1L, 1L, 50, 10, null
        );

        // Act
        estoque.adicionarQuantidade(25);

        // Assert
        assertEquals(75, estoque.getQuantidade());
    }

    @Test
    void deveRemoverQuantidade() {
        // Arrange
        EstoqueMedicamento estoque = new EstoqueMedicamento(
            1L, 1L, 1L, 50, 10, null
        );

        // Act
        estoque.removerQuantidade(20);

        // Assert
        assertEquals(30, estoque.getQuantidade());
    }

    @Test
    void naoDevePermitirQuantidadeNegativaAoRemover() {
        // Arrange
        EstoqueMedicamento estoque = new EstoqueMedicamento(
            1L, 1L, 1L, 10, 5, null
        );

        // Act
        estoque.removerQuantidade(20);

        // Assert
        assertEquals(0, estoque.getQuantidade()); // Deve ficar em 0, não negativo
    }

    @Test
    void deveDefinirNovaQuantidade() {
        // Arrange
        EstoqueMedicamento estoque = new EstoqueMedicamento(
            1L, 1L, 1L, 50, 10, null
        );

        // Act
        estoque.definirQuantidade(100);

        // Assert
        assertEquals(100, estoque.getQuantidade());
    }

    @Test
    void naoDeveAdicionarQuantidadeNegativa() {
        // Arrange
        EstoqueMedicamento estoque = new EstoqueMedicamento(
            1L, 1L, 1L, 50, 10, null
        );
        int quantidadeInicial = estoque.getQuantidade();

        // Act
        estoque.adicionarQuantidade(-10);

        // Assert
        assertEquals(quantidadeInicial, estoque.getQuantidade()); // Não deve ter mudado
    }

    @Test
    void naoDeveRemoverQuantidadeNegativa() {
        // Arrange
        EstoqueMedicamento estoque = new EstoqueMedicamento(
            1L, 1L, 1L, 50, 10, null
        );
        int quantidadeInicial = estoque.getQuantidade();

        // Act
        estoque.removerQuantidade(-10);

        // Assert
        assertEquals(quantidadeInicial, estoque.getQuantidade()); // Não deve ter mudado
    }

    @Test
    void naoDeveDefinirQuantidadeNegativa() {
        // Arrange
        EstoqueMedicamento estoque = new EstoqueMedicamento(
            1L, 1L, 1L, 50, 10, null
        );
        int quantidadeInicial = estoque.getQuantidade();

        // Act
        estoque.definirQuantidade(-10);

        // Assert
        assertEquals(quantidadeInicial, estoque.getQuantidade()); // Não deve ter mudado
    }
}
