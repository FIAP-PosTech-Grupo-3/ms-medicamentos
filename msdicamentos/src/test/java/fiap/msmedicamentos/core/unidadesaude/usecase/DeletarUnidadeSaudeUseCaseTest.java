package fiap.msmedicamentos.core.unidadesaude.usecase;

import fiap.msmedicamentos.core.estoque.gateway.EstoqueGateway;
import fiap.msmedicamentos.core.unidadesaude.exception.UnidadeSaudeNaoEncontradaException;
import fiap.msmedicamentos.core.unidadesaude.gateway.UnidadeSaudeGateway;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeletarUnidadeSaudeUseCaseTest {

    @Mock
    private UnidadeSaudeGateway unidadeSaudeGateway;

    @Mock
    private EstoqueGateway estoqueGateway;

    @InjectMocks
    private DeletarUnidadeSaudeUseCase deletarUnidadeSaudeUseCase;

    @Test
    void deveDeletarUnidadeSaudeComSucesso() {
        // Arrange
        Long unidadeId = 1L;
        when(unidadeSaudeGateway.existePorId(unidadeId)).thenReturn(true);
        when(estoqueGateway.buscarPorUnidadeSaude(unidadeId)).thenReturn(Collections.emptyList());

        // Act
        assertDoesNotThrow(() -> deletarUnidadeSaudeUseCase.execute(unidadeId));

        // Assert
        verify(unidadeSaudeGateway).existePorId(unidadeId);
        verify(estoqueGateway).buscarPorUnidadeSaude(unidadeId);
        verify(unidadeSaudeGateway).deletar(unidadeId);
    }

    @Test
    void deveLancarExcecaoQuandoUnidadeNaoExistir() {
        // Arrange
        Long unidadeId = 999L;
        when(unidadeSaudeGateway.existePorId(unidadeId)).thenReturn(false);

        // Act & Assert
        UnidadeSaudeNaoEncontradaException exception = assertThrows(
            UnidadeSaudeNaoEncontradaException.class,
            () -> deletarUnidadeSaudeUseCase.execute(unidadeId)
        );

        assertEquals("Unidade de saúde não encontrada com ID: 999", exception.getMessage());
        verify(unidadeSaudeGateway).existePorId(unidadeId);
        verify(unidadeSaudeGateway, never()).deletar(unidadeId);
    }

    @Test
    void deveLancarExcecaoParaIdNulo() {
        // Act & Assert
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> deletarUnidadeSaudeUseCase.execute(null)
        );

        assertEquals("ID da unidade de saúde é obrigatório", exception.getMessage());
        verify(unidadeSaudeGateway, never()).existePorId(any());
        verify(unidadeSaudeGateway, never()).deletar(any());
    }

    @Test
    void deveLancarExcecaoParaIdZero() {
        // Arrange
        Long unidadeId = 0L;
        when(unidadeSaudeGateway.existePorId(unidadeId)).thenReturn(false);

        // Act & Assert
        UnidadeSaudeNaoEncontradaException exception = assertThrows(
            UnidadeSaudeNaoEncontradaException.class,
            () -> deletarUnidadeSaudeUseCase.execute(unidadeId)
        );

        assertEquals("Unidade de saúde não encontrada com ID: 0", exception.getMessage());
        verify(unidadeSaudeGateway).existePorId(unidadeId);
        verify(unidadeSaudeGateway, never()).deletar(any());
    }

    @Test
    void deveLancarExcecaoParaIdNegativo() {
        // Arrange
        Long unidadeId = -5L;
        when(unidadeSaudeGateway.existePorId(unidadeId)).thenReturn(false);

        // Act & Assert
        UnidadeSaudeNaoEncontradaException exception = assertThrows(
            UnidadeSaudeNaoEncontradaException.class,
            () -> deletarUnidadeSaudeUseCase.execute(unidadeId)
        );

        assertEquals("Unidade de saúde não encontrada com ID: -5", exception.getMessage());
        verify(unidadeSaudeGateway).existePorId(unidadeId);
        verify(unidadeSaudeGateway, never()).deletar(any());
    }
}
