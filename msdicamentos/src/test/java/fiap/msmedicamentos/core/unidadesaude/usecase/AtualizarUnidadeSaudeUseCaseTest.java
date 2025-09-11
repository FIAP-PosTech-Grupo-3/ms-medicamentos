package fiap.msmedicamentos.core.unidadesaude.usecase;

import fiap.msmedicamentos.core.unidadesaude.entity.UnidadeSaude;
import fiap.msmedicamentos.core.unidadesaude.exception.UnidadeSaudeInvalidaException;
import fiap.msmedicamentos.core.unidadesaude.exception.UnidadeSaudeNaoEncontradaException;
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
class AtualizarUnidadeSaudeUseCaseTest {

    @Mock
    private UnidadeSaudeGateway unidadeSaudeGateway;

    @InjectMocks
    private AtualizarUnidadeSaudeUseCase atualizarUnidadeSaudeUseCase;

    private UnidadeSaude unidadeSaudeValida;

    @BeforeEach
    void setUp() {
        unidadeSaudeValida = new UnidadeSaude();
        unidadeSaudeValida.setNome("Hospital São Paulo");
        unidadeSaudeValida.setEndereco("Rua das Flores, 123");
        unidadeSaudeValida.setAtiva(true);
    }

    @Test
    void deveAtualizarUnidadeSaudeComSucesso() {
        // Arrange
        Long unidadeId = 1L;
        UnidadeSaude unidadeSaudeAtualizada = new UnidadeSaude();
        unidadeSaudeAtualizada.setId(unidadeId);
        unidadeSaudeAtualizada.setNome("Hospital São Paulo");
        unidadeSaudeAtualizada.setEndereco("Rua das Flores, 123");
        unidadeSaudeAtualizada.setAtiva(true);

        when(unidadeSaudeGateway.existePorId(unidadeId)).thenReturn(true);
        when(unidadeSaudeGateway.atualizar(any(UnidadeSaude.class))).thenReturn(unidadeSaudeAtualizada);

        // Act
        UnidadeSaude resultado = atualizarUnidadeSaudeUseCase.execute(unidadeId, unidadeSaudeValida);

        // Assert
        assertNotNull(resultado);
        assertEquals(unidadeId, resultado.getId());
        assertEquals("Hospital São Paulo", resultado.getNome());
        
        verify(unidadeSaudeGateway).existePorId(unidadeId);
        verify(unidadeSaudeGateway).atualizar(any(UnidadeSaude.class));
    }

    @Test
    void deveLancarExcecaoQuandoUnidadeNaoEncontrada() {
        // Arrange
        Long unidadeId = 999L;
        when(unidadeSaudeGateway.existePorId(unidadeId)).thenReturn(false);

        // Act & Assert
        UnidadeSaudeNaoEncontradaException exception = assertThrows(UnidadeSaudeNaoEncontradaException.class, () -> {
            atualizarUnidadeSaudeUseCase.execute(unidadeId, unidadeSaudeValida);
        });

        assertTrue(exception.getMessage().contains("999"));
        verify(unidadeSaudeGateway).existePorId(unidadeId);
        verify(unidadeSaudeGateway, never()).atualizar(any());
    }

    @Test
    void deveLancarExcecaoQuandoUnidadeSaudeInvalida() {
        // Arrange
        Long unidadeId = 1L;
        UnidadeSaude unidadeInvalida = new UnidadeSaude();
        unidadeInvalida.setNome("");  // nome vazio - inválido
        unidadeInvalida.setEndereco("Rua das Flores, 123");
        unidadeInvalida.setAtiva(true);

        when(unidadeSaudeGateway.existePorId(unidadeId)).thenReturn(true);

        // Act & Assert
        UnidadeSaudeInvalidaException exception = assertThrows(UnidadeSaudeInvalidaException.class, () -> {
            atualizarUnidadeSaudeUseCase.execute(unidadeId, unidadeInvalida);
        });

        assertEquals("Dados da unidade de saúde são inválidos", exception.getMessage());
        verify(unidadeSaudeGateway).existePorId(unidadeId);
        verify(unidadeSaudeGateway, never()).atualizar(any());
    }

    @Test
    void deveLancarExcecaoQuandoEnderecoInvalido() {
        // Arrange
        Long unidadeId = 1L;
        UnidadeSaude unidadeInvalida = new UnidadeSaude();
        unidadeInvalida.setNome("Hospital São Paulo");
        unidadeInvalida.setEndereco("");  // endereço vazio - inválido
        unidadeInvalida.setAtiva(true);

        when(unidadeSaudeGateway.existePorId(unidadeId)).thenReturn(true);

        // Act & Assert
        assertThrows(UnidadeSaudeInvalidaException.class, () -> {
            atualizarUnidadeSaudeUseCase.execute(unidadeId, unidadeInvalida);
        });

        verify(unidadeSaudeGateway).existePorId(unidadeId);
        verify(unidadeSaudeGateway, never()).atualizar(any());
    }

    @Test
    void deveDefinirIdCorretamenteNaUnidadeSaude() {
        // Arrange
        Long unidadeId = 5L;
        when(unidadeSaudeGateway.existePorId(unidadeId)).thenReturn(true);
        when(unidadeSaudeGateway.atualizar(any(UnidadeSaude.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        atualizarUnidadeSaudeUseCase.execute(unidadeId, unidadeSaudeValida);

        // Assert - verifica se o ID foi setado corretamente
        verify(unidadeSaudeGateway).atualizar(argThat(unidade -> 
            unidade.getId().equals(unidadeId)
        ));
    }
}
