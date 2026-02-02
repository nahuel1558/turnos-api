package apiTurnos.common.dto;
/**
 * Respuesta simple para devolver un id creado/afectado.
 * Ej: al crear un turno -> { "id": 123 }
 */
public record IdResponse(Long id) {}
