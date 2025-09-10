package fiap.msmedicamentos.core.unidadesaude.usecase;

import fiap.msmedicamentos.core.unidadesaude.entity.UnidadeSaude;
import fiap.msmedicamentos.core.unidadesaude.exception.UnidadeSaudeInvalidaException;
import fiap.msmedicamentos.core.unidadesaude.gateway.UnidadeSaudeGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CadastrarUnidadeSaudeUseCaseTest {

    @Mock
    private UnidadeSaudeGateway unidadeSaudeGateway;

    @InjectMocks
    private CadastrarUnidadeSaudeUseCase cadastrarUnidadeSaudeUseCase;

    private UnidadeSaude unidadeSaudeValida;

    @BeforeEach
    void setUp() {
        unidadeSaudeValida = new UnidadeSaude(
            null,
            "UBS Central",
            "Rua das Flores, 123",
            true
        );
    }

    @Test
    void deveCadastrarUnidadeSaudeComSucesso() {
        // Arrange
        UnidadeSaude unidadeSaudeSalva = new UnidadeSaude(
            1L,
            "UBS Central",
            "Rua das Flores, 123",
            true
        );

        when(unidadeSaudeGateway.salvar(any(UnidadeSaude.class))).thenReturn(unidadeSaudeSalva);

        // Act
        UnidadeSaude resultado = cadastrarUnidadeSaudeUseCase.execute(unidadeSaudeValida);

        // Assert
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("UBS Central", resultado.getNome());
        assertEquals("Rua das Flores, 123", resultado.getEndereco());
        assertTrue(resultado.isAtiva());
        verify(unidadeSaudeGateway).salvar(unidadeSaudeValida);
    }

    @Test
    void deveLancarExcecaoQuandoUnidadeSaudeInvalida() {
        // Arrange
        UnidadeSaude unidadeSaudeInvalida = new UnidadeSaude(
            null,
            "",  // nome vazio
            "Rua das Flores, 123",
            true
        );

        // Act & Assert
        assertThrows(UnidadeSaudeInvalidaException.class, () -> {
            cadastrarUnidadeSaudeUseCase.execute(unidadeSaudeInvalida);
        });

        verify(unidadeSaudeGateway, never()).salvar(any());
    }

    @Test
    void deveLancarExcecaoQuandoEnderecoVazio() {
        // Arrange
        UnidadeSaude unidadeSaudeInvalida = new UnidadeSaude(
            null,
            "UBS Central",
            "",  // endereco vazio
            true
        );

        // Act & Assert
        assertThrows(UnidadeSaudeInvalidaException.class, () -> {
            cadastrarUnidadeSaudeUseCase.execute(unidadeSaudeInvalida);
        });

        verify(unidadeSaudeGateway, never()).salvar(any());
    }

    @Test
    void deveLancarExcecaoQuandoUnidadeSaudeNula() {
        // Act & Assert
        assertThrows(NullPointerException.class, () -> {
            cadastrarUnidadeSaudeUseCase.execute(null);
        });

        verify(unidadeSaudeGateway, never()).salvar(any());
    }
}
