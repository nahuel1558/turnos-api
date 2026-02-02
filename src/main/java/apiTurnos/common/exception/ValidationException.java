package apiTurnos.common.exception;

import apiTurnos.common.constants.ErrorCodes;

import java.util.List;

/**
 * Validación propia de negocio (no confundir con @Valid).
 * Ej: "La fecha no puede ser en el pasado", "Duración inválida", etc.
 *
 * Permite adjuntar detalles (field -> issue).
 */
public class ValidationException extends DomainException {

    private final List<ApiError.ApiErrorDetail> details;

    public ValidationException(String message) {
        super(ErrorCodes.VALIDATION_ERROR, message);
        this.details = List.of();
    }

    public ValidationException(String message, List<ApiError.ApiErrorDetail> details) {
        super(ErrorCodes.VALIDATION_ERROR, message);
        this.details = details == null ? List.of() : details;
    }

    public List<ApiError.ApiErrorDetail> getDetails() {
        return details;
    }
}
