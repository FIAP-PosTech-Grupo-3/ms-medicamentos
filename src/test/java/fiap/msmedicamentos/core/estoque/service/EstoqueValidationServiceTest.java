package fiap.msmedicamentos.core.estoque.service;

import fiap.msmedicamentos.core.estoque.exception.EstoqueInvalidoException;
import fiap.msmedicamentos.core.medicamento.gateway.MedicamentoGateway;
import fiap.msmedicamentos.core.unidadesaude.gateway.UnidadeSaudeGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EstoqueValidationServiceTest {

    @Mock
    private MedicamentoGateway medicamentoGateway;

    @Mock
    private UnidadeSaudeGateway unidadeSaudeGateway;

    @InjectMocks
    private EstoqueValidationService estoqueValidationService;

    @Test
    void deveValidarQuantidadePositivaComSucesso() {
        // Arrange
        Integer quantidade = 10;

        // Act & Assert
        assertDoesNotThrow(() -> estoqueValidationService.validarQuantidadePositiva(quantidade));
    }

    @Test
    void deveLancarExcecaoQuandoQuantidadeForNula() {
        // Arrange
        Integer quantidade = null;

        // Act & Assert
        EstoqueInvalidoException exception = assertThrows(
            EstoqueInvalidoException.class,
            () -> estoqueValidationService.validarQuantidadePositiva(quantidade)
        );

        assertEquals("Quantidade deve ser maior que zero", exception.getMessage());
    }

    @Test
    void deveLancarExcecaoQuandoQuantidadeForZero() {
        // Arrange
        Integer quantidade = 0;

        // Act & Assert
        EstoqueInvalidoException exception = assertThrows(
            EstoqueInvalidoException.class,
            () -> estoqueValidationService.validarQuantidadePositiva(quantidade)
        );

        assertEquals("Quantidade deve ser maior que zero", exception.getMessage());
    }

    @Test
    void deveLancarExcecaoQuandoQuantidadeForNegativa() {
        // Arrange
        Integer quantidade = -5;

        // Act & Assert
        EstoqueInvalidoException exception = assertThrows(
            EstoqueInvalidoException.class,
            () -> estoqueValidationService.validarQuantidadePositiva(quantidade)
        );

        assertEquals("Quantidade deve ser maior que zero", exception.getMessage());
    }

    @Test
    void deveValidarQuantidadeNaoNegativaComSucesso() {
        // Arrange
        Integer quantidade = 10;

        // Act & Assert
        assertDoesNotThrow(() -> estoqueValidationService.validarQuantidadeNaoNegativa(quantidade));
    }

    @Test
    void deveValidarQuantidadeZeroComoNaoNegativa() {
        // Arrange
        Integer quantidade = 0;

        // Act & Assert
        assertDoesNotThrow(() -> estoqueValidationService.validarQuantidadeNaoNegativa(quantidade));
    }

    @Test
    void deveLancarExcecaoQuandoQuantidadeNaoNegativaForNula() {
        // Arrange
        Integer quantidade = null;

        // Act & Assert
        EstoqueInvalidoException exception = assertThrows(
            EstoqueInvalidoException.class,
            () -> estoqueValidationService.validarQuantidadeNaoNegativa(quantidade)
        );

        assertEquals("Quantidade deve ser maior ou igual a zero", exception.getMessage());
    }

    @Test
    void deveLancarExcecaoQuandoQuantidadeNaoNegativaForNegativa() {
        // Arrange
        Integer quantidade = -1;

        // Act & Assert
        EstoqueInvalidoException exception = assertThrows(
            EstoqueInvalidoException.class,
            () -> estoqueValidationService.validarQuantidadeNaoNegativa(quantidade)
        );

        assertEquals("Quantidade deve ser maior ou igual a zero", exception.getMessage());
    }

    @Test
    void deveValidarExistenciaMedicamentoEUnidadeComSucesso() {
        // Arrange
        Long medicamentoId = 1L;
        Long unidadeSaudeId = 1L;

        when(medicamentoGateway.existePorId(medicamentoId)).thenReturn(true);
        when(unidadeSaudeGateway.existePorId(unidadeSaudeId)).thenReturn(true);

        // Act & Assert
        assertDoesNotThrow(() -> estoqueValidationService.validarExistenciaMedicamentoEUnidade(medicamentoId, unidadeSaudeId));

        verify(medicamentoGateway).existePorId(medicamentoId);
        verify(unidadeSaudeGateway).existePorId(unidadeSaudeId);
    }

    @Test
    void deveLancarExcecaoQuandoMedicamentoNaoExistir() {
        // Arrange
        Long medicamentoId = 999L;
        Long unidadeSaudeId = 1L;

        when(medicamentoGateway.existePorId(medicamentoId)).thenReturn(false);

        // Act & Assert
        EstoqueInvalidoException exception = assertThrows(
            EstoqueInvalidoException.class,
            () -> estoqueValidationService.validarExistenciaMedicamentoEUnidade(medicamentoId, unidadeSaudeId)
        );

        assertEquals("Medicamento não encontrado com ID: " + medicamentoId, exception.getMessage());
        
        verify(medicamentoGateway).existePorId(medicamentoId);
        verify(unidadeSaudeGateway, never()).existePorId(anyLong());
    }

    @Test
    void deveLancarExcecaoQuandoUnidadeSaudeNaoExistir() {
        // Arrange
        Long medicamentoId = 1L;
        Long unidadeSaudeId = 999L;

        when(medicamentoGateway.existePorId(medicamentoId)).thenReturn(true);
        when(unidadeSaudeGateway.existePorId(unidadeSaudeId)).thenReturn(false);

        // Act & Assert
        EstoqueInvalidoException exception = assertThrows(
            EstoqueInvalidoException.class,
            () -> estoqueValidationService.validarExistenciaMedicamentoEUnidade(medicamentoId, unidadeSaudeId)
        );

        assertEquals("Unidade de saúde não encontrada com ID: " + unidadeSaudeId, exception.getMessage());
        
        verify(medicamentoGateway).existePorId(medicamentoId);
        verify(unidadeSaudeGateway).existePorId(unidadeSaudeId);
    }
}
