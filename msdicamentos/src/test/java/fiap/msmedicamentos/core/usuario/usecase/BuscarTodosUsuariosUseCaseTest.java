package fiap.msmedicamentos.core.usuario.usecase;

import fiap.msmedicamentos.core.usuario.entity.Usuario;
import fiap.msmedicamentos.core.usuario.enums.PapelUsuario;
import fiap.msmedicamentos.core.usuario.gateway.UsuarioGateway;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BuscarTodosUsuariosUseCaseTest {

    @Mock
    private UsuarioGateway usuarioGateway;

    @InjectMocks
    private BuscarTodosUsuariosUseCase buscarTodosUsuariosUseCase;

    @Test
    void deveRetornarListaDeUsuarios() {
        // Arrange
        List<Usuario> usuarios = java.util.Arrays.asList(
            new Usuario(1L, "João", "joao@teste.com", "senha", PapelUsuario.ADMIN, true, java.time.LocalDateTime.now()),
            new Usuario(2L, "Maria", "maria@teste.com", "senha", PapelUsuario.ADMIN, true, java.time.LocalDateTime.now())
        );
        Page<Usuario> pagina = new PageImpl<>(usuarios);
        PageRequest pageRequest = PageRequest.of(0, 10);
        
        when(usuarioGateway.buscarTodos(pageRequest)).thenReturn(pagina);

        // Act
        Page<Usuario> resultado = buscarTodosUsuariosUseCase.execute(pageRequest);

        // Assert
        assertNotNull(resultado);
        assertEquals(2, resultado.getContent().size());
        assertEquals("João", resultado.getContent().get(0).getNome());
        verify(usuarioGateway).buscarTodos(pageRequest);
    }

    @Test
    void deveRetornarPaginaVazia() {
        // Arrange
        Page<Usuario> paginaVazia = new PageImpl<>(java.util.Arrays.asList());
        PageRequest pageRequest = PageRequest.of(0, 10);
        
        when(usuarioGateway.buscarTodos(pageRequest)).thenReturn(paginaVazia);

        // Act
        Page<Usuario> resultado = buscarTodosUsuariosUseCase.execute(pageRequest);

        // Assert
        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
        verify(usuarioGateway).buscarTodos(pageRequest);
    }
}
