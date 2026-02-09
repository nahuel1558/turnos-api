package apiTurnos.user.command;

import apiTurnos.user.model.Role;
import apiTurnos.user.model.UserStatus;

/**
 * Command CQRS: actualizar usuario.
 */
public record UpdateUserCommand(
        String id,
        String email,
        String firstName,
        String lastName,
        String phone,
        String password,
        Role role,
        UserStatus status
) { }

