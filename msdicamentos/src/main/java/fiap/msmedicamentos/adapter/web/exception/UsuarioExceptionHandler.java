package fiap.msmedicamentos.adapter.web.exception;

import fiap.msmedicamentos.core.usuario.exception.UsuarioInvalidoException;
import fiap.msmedicamentos.core.usuario.exception.UsuarioJaExisteException;
import fiap.msmedicamentos.core.usuario.exception.UsuarioNaoEncontradoException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class UsuarioExceptionHandler {

    @ExceptionHandler(UsuarioNaoEncontradoException.class)
    public ResponseEntity<Map<String, Object>> handleUsuarioNaoEncontrado(UsuarioNaoEncontradoException ex) {
        log.warn("Usuário não encontrado: {}", ex.getMessage());
        
        Map<String, Object> error = new HashMap<>();
        error.put("erro", "Usuário não encontrado");
        error.put("mensagem", ex.getMessage());
        error.put("status", HttpStatus.NOT_FOUND.value());
        error.put("timestamp", LocalDateTime.now());
        
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(UsuarioJaExisteException.class)
    public ResponseEntity<Map<String, Object>> handleUsuarioJaExiste(UsuarioJaExisteException ex) {
        log.warn("Usuário já existe: {}", ex.getMessage());
        
        Map<String, Object> error = new HashMap<>();
        error.put("erro", "Usuário já existe");
        error.put("mensagem", ex.getMessage());
        error.put("status", HttpStatus.CONFLICT.value());
        error.put("timestamp", LocalDateTime.now());
        
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    @ExceptionHandler(UsuarioInvalidoException.class)
    public ResponseEntity<Map<String, Object>> handleUsuarioInvalido(UsuarioInvalidoException ex) {
        log.warn("Dados de usuário inválidos: {}", ex.getMessage());
        
        Map<String, Object> error = new HashMap<>();
        error.put("erro", "Dados inválidos");
        error.put("mensagem", ex.getMessage());
        error.put("status", HttpStatus.BAD_REQUEST.value());
        error.put("timestamp", LocalDateTime.now());
        
        return ResponseEntity.badRequest().body(error);
    }
}
