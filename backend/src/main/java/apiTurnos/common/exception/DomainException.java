package apiTurnos.common.exception;
/**
 * Excepción base del dominio / aplicación.
 *
 * Se usa como raíz para errores esperables del negocio:
 * - NotFound
 * - Conflict
 * - Validation
 *
 * Ventaja:
 * - Permite capturar en GlobalExceptionHandler y responder ApiError consistente.
 */
public class DomainException extends RuntimeException{
    private final String code;

    public DomainException(String code, String message) {
        super(message);
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
