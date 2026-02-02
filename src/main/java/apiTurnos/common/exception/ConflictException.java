package apiTurnos.common.exception;

import apiTurnos.common.constants.ErrorCodes;

/**
 * Conflictos del negocio.
 * Ej: intentar reservar un turno ya ocupado.
 */
public class ConflictException extends DomainException {

    public ConflictException(String message) {
        super(ErrorCodes.CONFLICT, message);
    }
}