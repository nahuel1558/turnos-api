package apiTurnos.user.dto.response;

import apiTurnos.user.model.Role;
import apiTurnos.user.model.UserStatus;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * Response público del usuario (sin passwordHash).
 */
public record UserResponse(
        String id,
        String email,
        String firstName,
        String lastName,
        String phone,
        Set<String> roles,
        UserStatus status,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
){}

