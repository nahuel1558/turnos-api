package apiTurnos.client.query;

import jakarta.validation.constraints.NotNull;

/**
 * Query CQRS para obtener un Client por ID
 */
public record GetClientByIdQuery(
        @NotNull Long clientId
) {}
