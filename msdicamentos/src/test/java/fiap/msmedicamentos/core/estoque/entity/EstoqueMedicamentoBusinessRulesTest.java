package fiap.msmedicamentos.core.estoque.entity;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Teste focado na lógica de negócio real da entidade EstoqueMedicamento
 * sem usar mocks desnecessários - testa diretamente a lógica da entidade
 */
class EstoqueMedicamentoBusinessRulesTest {

    @Test
    @DisplayName("Deve calcular corretamente se precisa reposição")
    void deveCalcularSeNecessitaReposicao() {
        // Arrange & Act: Teste da lógica real sem mocks
        EstoqueMedicamento estoqueAbaixoMinimo = new EstoqueMedicamento(
            1L,
            1L,
            1L,
            5,   // quantidade atual
            10   // estoque mínimo
        );

        EstoqueMedicamento estoqueAcimaMinimo = new EstoqueMedicamento(
            2L,
            1L,
            1L,
            20,  // quantidade atual
            10   // estoque mínimo
        );

        EstoqueMedicamento estoqueExatoMinimo = new EstoqueMedicamento(
            3L,
            1L,
            1L,
            10,  // quantidade atual
            10   // estoque mínimo
        );

        // Assert: Testa regra de negócio real da entidade
        assertTrue(estoqueAbaixoMinimo.precisaReposicao());
        assertFalse(estoqueAcimaMinimo.precisaReposicao());
        assertTrue(estoqueExatoMinimo.precisaReposicao()); // A regra real usa <= (menor ou igual)
    }

    @Test
    @DisplayName("Deve validar operações de adição e remoção de estoque")
    void deveValidarOperacoesEstoque() {
        // Arrange
        EstoqueMedicamento estoque = new EstoqueMedicamento(
            1L,
            1L,
            1L,
            50,
            10
        );

        // Act & Assert: Testa lógica real de adição
        estoque.adicionarQuantidade(30);
        assertEquals(80, estoque.getQuantidade());

        // Act & Assert: Testa lógica real de remoção
        estoque.removerQuantidade(20);
        assertEquals(60, estoque.getQuantidade());

        // Act & Assert: Testa remoção que deixa o estoque em zero
        estoque.removerQuantidade(100); // Remove mais que o disponível
        assertEquals(0, estoque.getQuantidade()); // Deve ficar em 0, não negativo
    }

    @Test
    @DisplayName("Deve validar operações com quantidades inválidas")
    void deveValidarQuantidadesInvalidas() {
        // Arrange
        EstoqueMedicamento estoque = new EstoqueMedicamento(
            1L,
            1L,
            1L,
            50,
            10
        );
        
        int quantidadeOriginal = estoque.getQuantidade();

        // Act & Assert: Testa que quantidade negativa não altera o estoque
        estoque.adicionarQuantidade(-10);
        assertEquals(quantidadeOriginal, estoque.getQuantidade()); // Não deve ter mudado

        estoque.removerQuantidade(-5);
        assertEquals(quantidadeOriginal, estoque.getQuantidade()); // Não deve ter mudado

        // Act & Assert: Testa que quantidade zero não altera o estoque
        estoque.adicionarQuantidade(0);
        assertEquals(quantidadeOriginal, estoque.getQuantidade()); // Não deve ter mudado

        estoque.removerQuantidade(0);
        assertEquals(quantidadeOriginal, estoque.getQuantidade()); // Não deve ter mudado
    }

    @Test
    @DisplayName("Deve definir corretamente a quantidade")
    void deveDefinirQuantidadeCorretamente() {
        // Arrange
        EstoqueMedicamento estoque = new EstoqueMedicamento(
            1L,
            1L,
            1L,
            100,
            20
        );

        // Act & Assert: Testa definição de quantidade válida
        estoque.definirQuantidade(50);
        assertEquals(50, estoque.getQuantidade());
        
        estoque.definirQuantidade(0);
        assertEquals(0, estoque.getQuantidade());

        // Act & Assert: Testa que quantidade negativa não altera
        estoque.definirQuantidade(25);
        assertEquals(25, estoque.getQuantidade());
        
        estoque.definirQuantidade(-5); // Não deve alterar
        assertEquals(25, estoque.getQuantidade()); // Deve manter o valor anterior
    }

    @Test
    @DisplayName("Deve validar estado de criação da entidade")
    void deveValidarEstadoCriacaoEntidade() {
        // Act & Assert: Testa criação com valores válidos
        EstoqueMedicamento estoqueValido = new EstoqueMedicamento(
            1L,
            1L,
            1L,
            50,
            10
        );
        assertTrue(estoqueValido.isValido());

        // Act & Assert: Testa validação com medicamentoId nulo
        EstoqueMedicamento estoqueInvalido1 = new EstoqueMedicamento(
            1L,
            null,  // medicamentoId nulo
            1L,
            50,
            10
        );
        assertFalse(estoqueInvalido1.isValido());

        // Act & Assert: Testa validação com unidadeSaudeId nulo
        EstoqueMedicamento estoqueInvalido2 = new EstoqueMedicamento(
            1L,
            1L,
            null,  // unidadeSaudeId nulo
            50,
            10
        );
        assertFalse(estoqueInvalido2.isValido());

        // Act & Assert: Testa validação com quantidade negativa
        EstoqueMedicamento estoqueInvalido3 = new EstoqueMedicamento(
            1L,
            1L,
            1L,
            -10,  // quantidade negativa
            10
        );
        assertFalse(estoqueInvalido3.isValido());
    }
}
