package apiTurnos.security.dto;

public record AuthResponse(
        String token,
        String tokenType
) {}

