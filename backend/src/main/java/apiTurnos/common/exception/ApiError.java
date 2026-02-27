package apiTurnos.common.exception;

import java.time.OffsetDateTime;
import java.util.List;

/**
 * DTO de error para respuestas HTTP.
 * Este objeto es el que el cliente consume (Front / Mobile / Postman).
 *
 * Se recomienda:
 * - code: estable (para lógica del cliente)
 * - message: humano
 * - details: lista de detalles (errores de validación, campos, etc.)
 */
public record  ApiError (
        String code,
        String message,
        int status,
        String path,
        OffsetDateTime timestamp,
        List<ApiErrorDetail> details
) {

    /**
     * Detalle de error (por ejemplo validación).
     * field puede ser null cuando el error no aplica a un campo específico.
     */
    public record ApiErrorDetail(String field, String issue) {}
}
