package apiTurnos.client.query;

import jakarta.validation.constraints.NotBlank;

public record GetClientByUserIdQuery(
        @NotBlank String userId
) {}


