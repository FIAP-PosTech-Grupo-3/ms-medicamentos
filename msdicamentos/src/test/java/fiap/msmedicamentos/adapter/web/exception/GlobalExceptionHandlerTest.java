package fiap.msmedicamentos.adapter.web.exception;

import fiap.msmedicamentos.core.estoque.exception.EstoqueInvalidoException;
import fiap.msmedicamentos.core.estoque.exception.EstoqueNaoEncontradoException;
import fiap.msmedicamentos.core.medicamento.exception.MedicamentoException;
import fiap.msmedicamentos.core.medicamento.exception.MedicamentoInvalidoException;
import fiap.msmedicamentos.core.medicamento.exception.MedicamentoNaoEncontradoException;
import fiap.msmedicamentos.core.unidadesaude.exception.UnidadeSaudeInvalidaException;
import fiap.msmedicamentos.core.unidadesaude.exception.UnidadeSaudeNaoEncontradaException;
import fiap.msmedicamentos.core.usuario.exception.UsuarioInvalidoException;
import fiap.msmedicamentos.core.usuario.exception.UsuarioJaExisteException;
import fiap.msmedicamentos.core.usuario.exception.UsuarioNaoEncontradoException;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GlobalExceptionHandlerTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private MethodArgumentNotValidException methodArgumentNotValidException;

    @Mock
    private BindingResult bindingResult;

    @InjectMocks
    private GlobalExceptionHandler globalExceptionHandler;

    @BeforeEach
    void setUp() {
        when(request.getRequestURI()).thenReturn("/api/test");
    }

    @Test
    void deveHandleMethodArgumentNotValidException() {
        // Arrange
        FieldError fieldError = new FieldError("object", "field", "mensagem de erro");
        when(methodArgumentNotValidException.getBindingResult()).thenReturn(bindingResult);
        when(bindingResult.getAllErrors()).thenReturn(List.of(fieldError));

        // Act
        ResponseEntity<Map<String, Object>> response = globalExceptionHandler
                .handleValidationExceptions(methodArgumentNotValidException, request);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        
        Map<String, Object> body = response.getBody();
        assertEquals("Dados de entrada inválidos", body.get("erro"));
        assertEquals(400, body.get("status"));
        assertEquals("/api/test", body.get("path"));
        assertNotNull(body.get("timestamp"));
        
        @SuppressWarnings("unchecked")
        Map<String, String> campos = (Map<String, String>) body.get("campos");
        assertEquals("mensagem de erro", campos.get("field"));
    }

    @Test
    void deveHandleMedicamentoInvalidoException() {
        // Arrange
        MedicamentoInvalidoException ex = new MedicamentoInvalidoException("Medicamento inválido");

        // Act
        ResponseEntity<ErrorResponse> response = globalExceptionHandler
                .handleMedicamentoInvalido(ex, request);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Campo obrigatório: Medicamento inválido", response.getBody().getMessage());
        assertEquals(400, response.getBody().getStatus());
        assertEquals("/api/test", response.getBody().getPath());
        assertNotNull(response.getBody().getTimestamp());
    }

    @Test
    void deveHandleMedicamentoNaoEncontradoException() {
        // Arrange
        MedicamentoNaoEncontradoException ex = new MedicamentoNaoEncontradoException(999L);

        // Act
        ResponseEntity<ErrorResponse> response = globalExceptionHandler
                .handleMedicamentoNaoEncontrado(ex, request);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Medicamento não encontrado com ID: 999", response.getBody().getMessage());
        assertEquals(404, response.getBody().getStatus());
    }

    @Test
    void deveHandleMedicamentoException() {
        // Arrange
        MedicamentoException ex = new MedicamentoException("Erro de medicamento");

        // Act
        ResponseEntity<ErrorResponse> response = globalExceptionHandler
                .handleMedicamentoException(ex, request);

        // Assert
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Erro de medicamento", response.getBody().getMessage());
        assertEquals(422, response.getBody().getStatus());
    }

    @Test
    void deveHandleUnidadeSaudeInvalidaException() {
        // Arrange
        UnidadeSaudeInvalidaException ex = new UnidadeSaudeInvalidaException("Unidade de saúde inválida");

        // Act
        ResponseEntity<ErrorResponse> response = globalExceptionHandler
                .handleUnidadeSaudeInvalida(ex, request);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Unidade de saúde inválida", response.getBody().getMessage());
        assertEquals(400, response.getBody().getStatus());
    }

    @Test
    void deveHandleUnidadeSaudeNaoEncontradaException() {
        // Arrange
        UnidadeSaudeNaoEncontradaException ex = new UnidadeSaudeNaoEncontradaException("Unidade de saúde não encontrada");

        // Act
        ResponseEntity<ErrorResponse> response = globalExceptionHandler
                .handleUnidadeSaudeNaoEncontrada(ex, request);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Unidade de saúde não encontrada", response.getBody().getMessage());
        assertEquals(404, response.getBody().getStatus());
    }

    @Test
    void deveHandleEstoqueInvalidoException() {
        // Arrange
        EstoqueInvalidoException ex = new EstoqueInvalidoException("Estoque inválido");

        // Act
        ResponseEntity<ErrorResponse> response = globalExceptionHandler
                .handleEstoqueInvalido(ex, request);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Estoque inválido", response.getBody().getMessage());
        assertEquals(400, response.getBody().getStatus());
    }

    @Test
    void deveHandleEstoqueNaoEncontradoException() {
        // Arrange
        EstoqueNaoEncontradoException ex = new EstoqueNaoEncontradoException("Estoque não encontrado");

        // Act
        ResponseEntity<ErrorResponse> response = globalExceptionHandler
                .handleEstoqueNaoEncontrado(ex, request);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Estoque não encontrado", response.getBody().getMessage());
        assertEquals(404, response.getBody().getStatus());
    }

    @Test
    void deveHandleUsuarioNaoEncontradoException() {
        // Arrange
        UsuarioNaoEncontradoException ex = new UsuarioNaoEncontradoException("Usuário não encontrado");

        // Act
        ResponseEntity<ErrorResponse> response = globalExceptionHandler
                .handleUsuarioNaoEncontrado(ex, request);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Usuário não encontrado", response.getBody().getMessage());
        assertEquals(404, response.getBody().getStatus());
    }

    @Test
    void deveHandleUsuarioJaExisteException() {
        // Arrange
        UsuarioJaExisteException ex = new UsuarioJaExisteException("teste@email.com");

        // Act
        ResponseEntity<ErrorResponse> response = globalExceptionHandler
                .handleUsuarioJaExiste(ex, request);

        // Assert
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Usuário já existe com o email: teste@email.com", response.getBody().getMessage());
        assertEquals(409, response.getBody().getStatus());
    }

    @Test
    void deveHandleUsuarioInvalidoException() {
        // Arrange
        UsuarioInvalidoException ex = new UsuarioInvalidoException("Usuário inválido");

        // Act
        ResponseEntity<ErrorResponse> response = globalExceptionHandler
                .handleUsuarioInvalido(ex, request);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Usuário inválido", response.getBody().getMessage());
        assertEquals(400, response.getBody().getStatus());
    }

    @Test
    void deveHandleIllegalArgumentException() {
        // Arrange
        IllegalArgumentException ex = new IllegalArgumentException("Argumento inválido");

        // Act
        ResponseEntity<ErrorResponse> response = globalExceptionHandler
                .handleIllegalArgument(ex, request);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Argumento inválido", response.getBody().getMessage());
        assertEquals(400, response.getBody().getStatus());
    }

    @Test
    void deveHandleIllegalStateException() {
        // Arrange
        IllegalStateException ex = new IllegalStateException("Estado inválido");

        // Act
        ResponseEntity<ErrorResponse> response = globalExceptionHandler
                .handleIllegalState(ex, request);

        // Assert
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Estado inválido", response.getBody().getMessage());
        assertEquals(422, response.getBody().getStatus());
    }

    @Test
    void deveHandleGenericException() {
        // Arrange
        Exception ex = new RuntimeException("Erro genérico");

        // Act
        ResponseEntity<ErrorResponse> response = globalExceptionHandler
                .handleGenericException(ex, request);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Erro interno do servidor", response.getBody().getMessage());
        assertEquals(500, response.getBody().getStatus());
    }
}
