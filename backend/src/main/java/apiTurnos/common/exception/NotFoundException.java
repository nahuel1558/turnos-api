package apiTurnos.common.exception;

import apiTurnos.common.constants.ErrorCodes;

/**
 * Cuando un recurso no existe.
 * Ej: "Peluquero no encontrado", "Turno no encontrado", etc.
 */
public class NotFoundException extends DomainException{
    public NotFoundException(String message) {
        super(ErrorCodes.NOT_FOUND, message);
    }
}
