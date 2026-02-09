package apiTurnos.user.mapper;

import apiTurnos.user.dto.request.RegisterUserRequest;
import apiTurnos.user.dto.request.UpdateUserRequest;
import apiTurnos.user.dto.response.UserResponse;
import apiTurnos.user.model.Role;
import apiTurnos.user.model.UserAccount;
import apiTurnos.user.model.UserStatus;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Mapper de User (convierte entre DTOs y entidades).
 *
 * SRP:
 * - Solo mapeo (sin lógica de persistencia ni casos de uso).
 */
public class UserMapper {

    public UserAccount toNewEntity(RegisterUserRequest request, String passwordHash) {
        LocalDateTime now = LocalDateTime.now();

        return UserAccount.builder()
                .id(UUID.randomUUID().toString())
                .email(normalizeEmail(request.email()))
                .firstName(trim(request.firstName()))
                .lastName(trim(request.lastName()))
                .phone(trim(request.phone()))
                .passwordHash(passwordHash)
                .role(request.role() == null ? Role.CLIENT : request.role())
                .status(UserStatus.ACTIVE)
                .createdAt(now)
                .updatedAt(now)
                .build();
    }

    public void applyUpdates(UserAccount user, UpdateUserRequest request, String newPasswordHash) {

        if (request.email() != null && !request.email().isBlank()) {
            user.setEmail(normalizeEmail(request.email()));
        }
        if (request.firstName() != null && !request.firstName().isBlank()) {
            user.setFirstName(trim(request.firstName()));
        }
        if (request.lastName() != null && !request.lastName().isBlank()) {
            user.setLastName(trim(request.lastName()));
        }
        if (request.phone() != null && !request.phone().isBlank()){
            user.setPhone((trim(request.phone())));
        }
        if (request.role() != null) {
            user.setRole(request.role());
        }
        if (request.status() != null) {
            user.setStatus(request.status());
        }
        if (newPasswordHash != null) {
            user.setPasswordHash(newPasswordHash);
        }

        user.markUpdated(LocalDateTime.now());
    }

    public UserResponse toResponse(UserAccount user) {
        return new UserResponse(
                user.getId(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.getPhone(),
                user.getRole(),
                user.getStatus(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }

    private String normalizeEmail(String email) {
        return email == null ? null : email.trim().toLowerCase();
    }

    private String trim(String value) {
        return value == null ? null : value.trim();
    }
}

