package apiTurnos.common.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Respuesta genérica que devuelve un ID")
public record IdResponse(
        @Schema(description = "ID del recurso creado/afectado", example = "1")
        Long id
) {}

