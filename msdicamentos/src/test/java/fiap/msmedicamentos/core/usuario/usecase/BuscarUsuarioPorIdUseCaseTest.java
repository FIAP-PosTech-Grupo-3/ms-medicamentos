package fiap.msmedicamentos.core.usuario.usecase;

import fiap.msmedicamentos.core.usuario.entity.Usuario;
import fiap.msmedicamentos.core.usuario.enums.PapelUsuario;
import fiap.msmedicamentos.core.usuario.exception.UsuarioNaoEncontradoException;
import fiap.msmedicamentos.core.usuario.gateway.UsuarioGateway;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BuscarUsuarioPorIdUseCaseTest {

    @Mock
    private UsuarioGateway usuarioGateway;

    @InjectMocks
    private BuscarUsuarioPorIdUseCase buscarUsuarioPorIdUseCase;

    @Test
    void deveRetornarUsuarioQuandoEncontrado() {
        // Arrange
        Long usuarioId = 1L;
        Usuario usuario = new Usuario(1L, "João Silva", "joao@email.com", "senha123", PapelUsuario.USUARIO, true);
        
        when(usuarioGateway.buscarPorId(usuarioId)).thenReturn(Optional.of(usuario));

        // Act
        Usuario resultado = buscarUsuarioPorIdUseCase.execute(usuarioId);

        // Assert
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("João Silva", resultado.getNome());
        assertEquals("joao@email.com", resultado.getEmail());
        assertEquals(PapelUsuario.USUARIO, resultado.getPapel());
        assertTrue(resultado.getAtivo());
        verify(usuarioGateway).buscarPorId(usuarioId);
    }

    @Test
    void deveLancarExcecaoQuandoUsuarioNaoEncontrado() {
        // Arrange
        Long usuarioId = 999L;
        when(usuarioGateway.buscarPorId(usuarioId)).thenReturn(Optional.empty());

        // Act & Assert
        UsuarioNaoEncontradoException exception = assertThrows(
            UsuarioNaoEncontradoException.class,
            () -> buscarUsuarioPorIdUseCase.execute(usuarioId)
        );

        assertEquals("Usuário não encontrado com ID: 999", exception.getMessage());
        verify(usuarioGateway).buscarPorId(usuarioId);
    }

    @Test
    void deveLancarExcecaoParaIdNulo() {
        // Arrange
        when(usuarioGateway.buscarPorId(null)).thenReturn(Optional.empty());

        // Act & Assert
        UsuarioNaoEncontradoException exception = assertThrows(
            UsuarioNaoEncontradoException.class,
            () -> buscarUsuarioPorIdUseCase.execute(null)
        );

        assertEquals("Usuário não encontrado com ID: null", exception.getMessage());
        verify(usuarioGateway).buscarPorId(null);
    }

    @Test
    void deveLancarExcecaoParaIdZero() {
        // Arrange
        Long usuarioId = 0L;
        when(usuarioGateway.buscarPorId(usuarioId)).thenReturn(Optional.empty());

        // Act & Assert
        UsuarioNaoEncontradoException exception = assertThrows(
            UsuarioNaoEncontradoException.class,
            () -> buscarUsuarioPorIdUseCase.execute(usuarioId)
        );

        assertEquals("Usuário não encontrado com ID: 0", exception.getMessage());
        verify(usuarioGateway).buscarPorId(usuarioId);
    }

    @Test
    void deveLancarExcecaoParaIdNegativo() {
        // Arrange
        Long usuarioId = -1L;
        when(usuarioGateway.buscarPorId(usuarioId)).thenReturn(Optional.empty());

        // Act & Assert
        UsuarioNaoEncontradoException exception = assertThrows(
            UsuarioNaoEncontradoException.class,
            () -> buscarUsuarioPorIdUseCase.execute(usuarioId)
        );

        assertEquals("Usuário não encontrado com ID: -1", exception.getMessage());
        verify(usuarioGateway).buscarPorId(usuarioId);
    }

    @Test
    void deveRetornarUsuarioAtivoOuInativo() {
        // Arrange - Usuário inativo
        Long usuarioId = 2L;
        Usuario usuarioInativo = new Usuario(2L, "Maria Santos", "maria@email.com", "senha456", PapelUsuario.ADMIN, false);
        
        when(usuarioGateway.buscarPorId(usuarioId)).thenReturn(Optional.of(usuarioInativo));

        // Act
        Usuario resultado = buscarUsuarioPorIdUseCase.execute(usuarioId);

        // Assert
        assertNotNull(resultado);
        assertEquals(2L, resultado.getId());
        assertEquals("Maria Santos", resultado.getNome());
        assertFalse(resultado.getAtivo());
        verify(usuarioGateway).buscarPorId(usuarioId);
    }
}
