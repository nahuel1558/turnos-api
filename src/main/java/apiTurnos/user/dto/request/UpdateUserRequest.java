package apiTurnos.user.dto.request;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UpdateUserRequest(
        @Email @NotBlank String email,
        @NotBlank String password,
        @NotBlank String firstName,
        @NotBlank String lastName
) {}
