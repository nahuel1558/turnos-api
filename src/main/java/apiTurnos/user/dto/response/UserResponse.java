package apiTurnos.user.dto.response;

import jakarta.validation.constraints.NotBlank;

import java.util.Set;

public record UserResponse (
        Long id,
        String email,
        String firstName,
        String lastName,
        String phone,
        boolean enabled,
        Set<String> roles
) {}
