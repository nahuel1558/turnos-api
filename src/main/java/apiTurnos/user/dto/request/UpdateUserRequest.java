package apiTurnos.user.dto.request;

import apiTurnos.user.model.Role;
import apiTurnos.user.model.UserStatus;

/**
 * Request para actualizar usuario (parcial).
 * Si viene null, no se modifica.
 */
public record UpdateUserRequest(
        String email,
        String firstName,
        String lastName,
        String phone,
        String password,
        Role role,
        UserStatus status
) { }

