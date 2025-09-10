package fiap.msmedicamentos.core.usuario.usecase;

import fiap.msmedicamentos.core.usuario.exception.UsuarioNaoEncontradoException;
import fiap.msmedicamentos.core.usuario.gateway.UsuarioGateway;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeletarUsuarioUseCaseTest {

    @Mock
    private UsuarioGateway usuarioGateway;

    @InjectMocks
    private DeletarUsuarioUseCase deletarUsuarioUseCase;

    @Test
    void deveDeletarUsuarioComSucesso() {
        // Arrange
        Long usuarioId = 1L;
        when(usuarioGateway.existePorId(usuarioId)).thenReturn(true);

        // Act
        assertDoesNotThrow(() -> deletarUsuarioUseCase.execute(usuarioId));

        // Assert
        verify(usuarioGateway).existePorId(usuarioId);
        verify(usuarioGateway).deletar(usuarioId);
    }

    @Test
    void deveLancarExcecaoQuandoUsuarioNaoExistir() {
        // Arrange
        Long usuarioId = 999L;
        when(usuarioGateway.existePorId(usuarioId)).thenReturn(false);

        // Act & Assert
        UsuarioNaoEncontradoException exception = assertThrows(
            UsuarioNaoEncontradoException.class,
            () -> deletarUsuarioUseCase.execute(usuarioId)
        );

        assertEquals("Usuário não encontrado com ID: 999", exception.getMessage());
        verify(usuarioGateway).existePorId(usuarioId);
        verify(usuarioGateway, never()).deletar(usuarioId);
    }

    @Test
    void deveLancarExcecaoParaIdNulo() {
        // Arrange
        when(usuarioGateway.existePorId(null)).thenReturn(false);

        // Act & Assert
        UsuarioNaoEncontradoException exception = assertThrows(
            UsuarioNaoEncontradoException.class,
            () -> deletarUsuarioUseCase.execute(null)
        );

        assertEquals("Usuário não encontrado com ID: null", exception.getMessage());
        verify(usuarioGateway).existePorId(null);
        verify(usuarioGateway, never()).deletar(any());
    }

    @Test
    void deveLancarExcecaoParaIdZero() {
        // Arrange
        Long usuarioId = 0L;
        when(usuarioGateway.existePorId(usuarioId)).thenReturn(false);

        // Act & Assert
        UsuarioNaoEncontradoException exception = assertThrows(
            UsuarioNaoEncontradoException.class,
            () -> deletarUsuarioUseCase.execute(usuarioId)
        );

        assertEquals("Usuário não encontrado com ID: 0", exception.getMessage());
        verify(usuarioGateway).existePorId(usuarioId);
        verify(usuarioGateway, never()).deletar(any());
    }

    @Test
    void deveLancarExcecaoParaIdNegativo() {
        // Arrange
        Long usuarioId = -1L;
        when(usuarioGateway.existePorId(usuarioId)).thenReturn(false);

        // Act & Assert
        UsuarioNaoEncontradoException exception = assertThrows(
            UsuarioNaoEncontradoException.class,
            () -> deletarUsuarioUseCase.execute(usuarioId)
        );

        assertEquals("Usuário não encontrado com ID: -1", exception.getMessage());
        verify(usuarioGateway).existePorId(usuarioId);
        verify(usuarioGateway, never()).deletar(any());
    }
}
