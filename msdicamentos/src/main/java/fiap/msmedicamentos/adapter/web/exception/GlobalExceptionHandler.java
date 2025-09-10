package fiap.msmedicamentos.adapter.web.exception;

import fiap.msmedicamentos.core.medicamento.exception.MedicamentoException;
import fiap.msmedicamentos.core.medicamento.exception.MedicamentoInvalidoException;
import fiap.msmedicamentos.core.medicamento.exception.MedicamentoNaoEncontradoException;
import fiap.msmedicamentos.core.unidadesaude.exception.UnidadeSaudeInvalidaException;
import fiap.msmedicamentos.core.unidadesaude.exception.UnidadeSaudeNaoEncontradaException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationExceptions(
            MethodArgumentNotValidException ex, HttpServletRequest request) {
        
        Map<String, Object> response = new HashMap<>();
        Map<String, String> fieldErrors = new HashMap<>();
        
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            fieldErrors.put(fieldName, errorMessage);
        });

        response.put("erro", "Dados de entrada inválidos");
        response.put("campos", fieldErrors);
        response.put("status", HttpStatus.BAD_REQUEST.value());
        response.put("timestamp", LocalDateTime.now());
        response.put("path", request.getRequestURI());

        log.warn("Erro de validação na requisição {}: {}", request.getRequestURI(), fieldErrors);
        
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(MedicamentoInvalidoException.class)
    public ResponseEntity<ErrorResponse> handleMedicamentoInvalido(
            MedicamentoInvalidoException ex, HttpServletRequest request) {
        log.warn("Dados inválidos: {}", ex.getMessage());
        ErrorResponse error = new ErrorResponse(ex.getMessage(), 400, request.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(MedicamentoNaoEncontradoException.class)
    public ResponseEntity<ErrorResponse> handleMedicamentoNaoEncontrado(
            MedicamentoNaoEncontradoException ex, HttpServletRequest request) {
        log.warn("Medicamento não encontrado: {}", ex.getMessage());
        ErrorResponse error = new ErrorResponse(ex.getMessage(), 404, request.getRequestURI());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(MedicamentoException.class)
    public ResponseEntity<ErrorResponse> handleMedicamentoException(
            MedicamentoException ex, HttpServletRequest request) {
        log.error("Erro de medicamento: {}", ex.getMessage());
        ErrorResponse error = new ErrorResponse(ex.getMessage(), 422, request.getRequestURI());
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(error);
    }

    @ExceptionHandler(UnidadeSaudeInvalidaException.class)
    public ResponseEntity<ErrorResponse> handleUnidadeSaudeInvalida(
            UnidadeSaudeInvalidaException ex, HttpServletRequest request) {
        log.warn("Dados inválidos da unidade de saúde: {}", ex.getMessage());
        ErrorResponse error = new ErrorResponse(ex.getMessage(), 400, request.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(UnidadeSaudeNaoEncontradaException.class)
    public ResponseEntity<ErrorResponse> handleUnidadeSaudeNaoEncontrada(
            UnidadeSaudeNaoEncontradaException ex, HttpServletRequest request) {
        log.warn("Unidade de saúde não encontrada: {}", ex.getMessage());
        ErrorResponse error = new ErrorResponse(ex.getMessage(), 404, request.getRequestURI());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(
            Exception ex, HttpServletRequest request) {
        log.error("Erro interno: {}", ex.getMessage(), ex);
        ErrorResponse error = new ErrorResponse("Erro interno do servidor", 500, request.getRequestURI());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}
