package apiTurnos.user.command;

import apiTurnos.user.model.Role;

/**
 * Command CQRS: registrar usuario.
 */
public record RegisterUserCommand(
        String email,
        String firstName,
        String lastName,
        String phone,
        String password,
        Role role
) { }

