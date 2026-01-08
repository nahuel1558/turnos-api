package apiTurnos.user.dto.response;

import jakarta.validation.constraints.NotBlank;

public record UserResponse (
        @NotBlank String firstName,
        @NotBlank String lastName,
        String phone
) {}
