package apiTurnos.common.exception;

/*
  Esta clase maneja de manera global las excepciones, centraliza el tratamiento de errores en la API REST.
  Actua como un "interceptor" captura excepciones lanzadas por cualquier controller y las convierte en
  respuestas HTTP con formato JSON.
  */

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

// Marca esta clase como un componente que intercepta excepciones.
@RestControllerAdvice
public class GlobalExceptionHandler {


    // Metodo que maneja las excepciones de tipo "IllegalArgumentException" (indica que el argumento es invalido).
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleIllegalArgument(IllegalArgumentException ex){

        // Con map genero el cuerpo de la respuesta.
        Map<String, Object> body = new HashMap<>();

        // Fecha y hora del error.
        body.put("timestamp", LocalDateTime.now());
        // Mensaje de la excepcion.
        body.put("message", ex.getMessage());
        body.put("status", HttpStatus.BAD_REQUEST.value());

        return ResponseEntity.badRequest().body(body);
    }

    // Maneja los errores generados por la anotación @Valid.
    // Esta excepción se lanza cuando los datos enviados por el cliente no cumplen con las restricciones definidas en los DTOs.
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationExceptions(MethodArgumentNotValidException ex){
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.BAD_REQUEST.value());

        //Extrae los errores específicos por campo.
        Map<String, String> errors = new HashMap<>();
        //Itera sobre todos los errores de campo.
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage()));

        body.put("errors", errors);

        return ResponseEntity.badRequest().body(body);
    }
}
