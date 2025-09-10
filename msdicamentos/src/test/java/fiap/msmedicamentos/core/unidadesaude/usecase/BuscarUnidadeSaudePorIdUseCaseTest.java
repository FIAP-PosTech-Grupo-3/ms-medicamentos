package fiap.msmedicamentos.core.unidadesaude.usecase;

import fiap.msmedicamentos.core.unidadesaude.entity.UnidadeSaude;
import fiap.msmedicamentos.core.unidadesaude.exception.UnidadeSaudeNaoEncontradaException;
import fiap.msmedicamentos.core.unidadesaude.gateway.UnidadeSaudeGateway;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BuscarUnidadeSaudePorIdUseCaseTest {

    @Mock
    private UnidadeSaudeGateway unidadeSaudeGateway;

    @InjectMocks
    private BuscarUnidadeSaudePorIdUseCase buscarUnidadeSaudePorIdUseCase;

    @Test
    void deveRetornarUnidadeSaudeQuandoEncontrada() {
        // Arrange
        Long unidadeId = 1L;
        UnidadeSaude unidadeSaude = new UnidadeSaude(1L, "Hospital Central", "Rua das Flores, 123", true);
        
        when(unidadeSaudeGateway.buscarPorId(unidadeId)).thenReturn(Optional.of(unidadeSaude));

        // Act
        UnidadeSaude resultado = buscarUnidadeSaudePorIdUseCase.execute(unidadeId);

        // Assert
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Hospital Central", resultado.getNome());
        assertEquals("Rua das Flores, 123", resultado.getEndereco());
        assertTrue(resultado.isAtiva());
        verify(unidadeSaudeGateway).buscarPorId(unidadeId);
    }

    @Test
    void deveLancarExcecaoQuandoUnidadeNaoEncontrada() {
        // Arrange
        Long unidadeId = 999L;
        when(unidadeSaudeGateway.buscarPorId(unidadeId)).thenReturn(Optional.empty());

        // Act & Assert
        UnidadeSaudeNaoEncontradaException exception = assertThrows(
            UnidadeSaudeNaoEncontradaException.class,
            () -> buscarUnidadeSaudePorIdUseCase.execute(unidadeId)
        );

        assertEquals("Unidade de saúde não encontrada com ID: 999", exception.getMessage());
        verify(unidadeSaudeGateway).buscarPorId(unidadeId);
    }

    @Test
    void deveLancarExcecaoParaIdNulo() {
        // Arrange
        when(unidadeSaudeGateway.buscarPorId(null)).thenReturn(Optional.empty());

        // Act & Assert
        UnidadeSaudeNaoEncontradaException exception = assertThrows(
            UnidadeSaudeNaoEncontradaException.class,
            () -> buscarUnidadeSaudePorIdUseCase.execute(null)
        );

        assertEquals("Unidade de saúde não encontrada com ID: null", exception.getMessage());
        verify(unidadeSaudeGateway).buscarPorId(null);
    }

    @Test
    void deveLancarExcecaoParaIdZero() {
        // Arrange
        Long unidadeId = 0L;
        when(unidadeSaudeGateway.buscarPorId(unidadeId)).thenReturn(Optional.empty());

        // Act & Assert
        UnidadeSaudeNaoEncontradaException exception = assertThrows(
            UnidadeSaudeNaoEncontradaException.class,
            () -> buscarUnidadeSaudePorIdUseCase.execute(unidadeId)
        );

        assertEquals("Unidade de saúde não encontrada com ID: 0", exception.getMessage());
        verify(unidadeSaudeGateway).buscarPorId(unidadeId);
    }

    @Test
    void deveRetornarUnidadeAtivaOuInativa() {
        // Arrange - Unidade inativa
        Long unidadeId = 2L;
        UnidadeSaude unidadeInativa = new UnidadeSaude(2L, "Clínica Desativada", "Av. Principal, 456", false);
        
        when(unidadeSaudeGateway.buscarPorId(unidadeId)).thenReturn(Optional.of(unidadeInativa));

        // Act
        UnidadeSaude resultado = buscarUnidadeSaudePorIdUseCase.execute(unidadeId);

        // Assert
        assertNotNull(resultado);
        assertEquals(2L, resultado.getId());
        assertEquals("Clínica Desativada", resultado.getNome());
        assertFalse(resultado.isAtiva());
        verify(unidadeSaudeGateway).buscarPorId(unidadeId);
    }
}
