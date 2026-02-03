package apiTurnos.user.dto.request;

import apiTurnos.user.model.Role;

/**
 * Request para registrar usuario.
 */
public record RegisterUserRequest(
        String email,
        String firstName,
        String lastName,
        String password,
        Role role
) { }

