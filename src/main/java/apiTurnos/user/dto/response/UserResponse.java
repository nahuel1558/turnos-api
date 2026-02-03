package apiTurnos.user.dto.response;

import apiTurnos.user.model.Role;
import apiTurnos.user.model.UserStatus;

import java.time.LocalDateTime;

/**
 * Response público del usuario (sin passwordHash).
 */
public record UserResponse(
        String id,
        String email,
        String firstName,
        String lastName,
        Role role,
        UserStatus status,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) { }

