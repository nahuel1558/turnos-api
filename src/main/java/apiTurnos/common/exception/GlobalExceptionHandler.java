package apiTurnos.common.exception;

import apiTurnos.common.constants.ErrorCodes;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.OffsetDateTime;
import java.util.List;

/**
 * Manejador global de excepciones.
 *
 * ¿Qué resuelve?
 * - Evita respuestas diferentes según quién lance el error.
 * - Da consistencia al front: siempre recibe ApiError.
 *
 * Nota:
 * - No hace lógica de negocio, solo mapea excepciones -> HTTP response.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Errores del dominio/aplicación que nosotros definimos.
     * Se decide el status según el tipo concreto.
     */
    @ExceptionHandler(DomainException.class)
    public ResponseEntity<ApiError> handleDomainException(DomainException ex, HttpServletRequest request) {

        HttpStatus status;

        if (ex instanceof NotFoundException) {
            status = HttpStatus.NOT_FOUND;
        } else if (ex instanceof ConflictException) {
            status = HttpStatus.CONFLICT;
        } else if (ex instanceof ValidationException) {
            status = HttpStatus.BAD_REQUEST;
        } else {
            status = HttpStatus.BAD_REQUEST;
        }


        List<ApiError.ApiErrorDetail> details = List.of();

        // Si es ValidationException, puede traer detalles
        if (ex instanceof ValidationException ve) {
            details = ve.getDetails();
        }

        ApiError body = new ApiError(
                ex.getCode(),
                ex.getMessage(),
                status.value(),
                request.getRequestURI(),
                OffsetDateTime.now(),
                details
        );

        return ResponseEntity.status(status).body(body);
    }

    /**
     * Validaciones de Spring (cuando uses @Valid).
     * Aunque hoy no quieras validaciones, lo dejamos armado para el futuro.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                 HttpServletRequest request) {

        List<ApiError.ApiErrorDetail> details = ex.getBindingResult().getFieldErrors().stream()
                .map(err -> new ApiError.ApiErrorDetail(err.getField(), err.getDefaultMessage()))
                .toList();

        ApiError body = new ApiError(
                ErrorCodes.VALIDATION_ERROR,
                "Error de validación",
                HttpStatus.BAD_REQUEST.value(),
                request.getRequestURI(),
                OffsetDateTime.now(),
                details
        );

        return ResponseEntity.badRequest().body(body);
    }

    /**
     * IllegalArgumentException suele aparecer con validaciones manuales.
     * Lo mapeamos a 400 para no tirar 500 al cliente.
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiError> handleIllegalArgument(IllegalArgumentException ex,
                                                          HttpServletRequest request) {

        ApiError body = new ApiError(
                ErrorCodes.VALIDATION_ERROR,
                ex.getMessage(),
                HttpStatus.BAD_REQUEST.value(),
                request.getRequestURI(),
                OffsetDateTime.now(),
                List.of()
        );

        return ResponseEntity.badRequest().body(body);
    }

    /**
     * Fallback final: cualquier error inesperado -> 500.
     * No exponemos stacktrace al cliente.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGeneric(Exception ex, HttpServletRequest request) {

        ApiError body = new ApiError(
                ErrorCodes.INTERNAL_ERROR,
                "Ocurrió un error inesperado",
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                request.getRequestURI(),
                OffsetDateTime.now(),
                List.of()
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }
}