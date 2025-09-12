package fiap.msmedicamentos.core.usuario.usecase;

import fiap.msmedicamentos.core.usuario.entity.Usuario;
import fiap.msmedicamentos.core.usuario.enums.PapelUsuario;
import fiap.msmedicamentos.core.usuario.exception.UsuarioInvalidoException;
import fiap.msmedicamentos.core.usuario.exception.UsuarioJaExisteException;
import fiap.msmedicamentos.core.usuario.gateway.UsuarioGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CadastrarUsuarioUseCaseTest {

    @Mock
    private UsuarioGateway usuarioGateway;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private CadastrarUsuarioUseCase cadastrarUsuarioUseCase;

    private Usuario usuarioValido;

    @BeforeEach
    void setUp() {
        usuarioValido = new Usuario(
            null,
            "João Silva",
            "teste@teste.com",
            "senha123",
            PapelUsuario.ADMIN,
            true,
            null
        );
    }

    @Test
    void deveCadastrarUsuarioComSucesso() {
        // Arrange
        Usuario usuarioSalvo = new Usuario(
            1L,
            "João Silva",
            "teste@teste.com",
            "senhaEncriptada",
            PapelUsuario.ADMIN,
            true,
            java.time.LocalDateTime.now()
        );

        when(usuarioGateway.existePorEmail("teste@teste.com")).thenReturn(false);
        when(passwordEncoder.encode("senha123")).thenReturn("senhaEncriptada");
        when(usuarioGateway.salvar(any(Usuario.class))).thenReturn(usuarioSalvo);

        // Act
        Usuario resultado = cadastrarUsuarioUseCase.execute(usuarioValido);

        // Assert
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("teste@teste.com", resultado.getEmail());
        verify(usuarioGateway).existePorEmail("teste@teste.com");
        verify(passwordEncoder).encode("senha123");
        verify(usuarioGateway).salvar(any(Usuario.class));
    }

    @Test
    void deveLancarExcecaoQuandoUsuarioInvalido() {
        // Arrange
        Usuario usuarioInvalido = new Usuario(
            null,
            "João Silva",
            "",  // email vazio
            "senha123",
            PapelUsuario.ADMIN,
            true,
            null
        );

        // Act & Assert
        assertThrows(UsuarioInvalidoException.class, () -> {
            cadastrarUsuarioUseCase.execute(usuarioInvalido);
        });

        verify(usuarioGateway, never()).existePorEmail(any());
        verify(usuarioGateway, never()).salvar(any());
    }

    @Test
    void deveLancarExcecaoQuandoEmailJaExiste() {
        // Arrange
        when(usuarioGateway.existePorEmail("teste@teste.com")).thenReturn(true);

        // Act & Assert
        assertThrows(UsuarioJaExisteException.class, () -> {
            cadastrarUsuarioUseCase.execute(usuarioValido);
        });

        verify(usuarioGateway).existePorEmail("teste@teste.com");
        verify(usuarioGateway, never()).salvar(any());
    }
}
