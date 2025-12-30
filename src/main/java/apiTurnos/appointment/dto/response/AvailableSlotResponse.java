package apiTurnos.appointment.dto.response;

import java.time.LocalTime;

/**
 * DTO de salida que representa un horario disponible.
 *
 * Patrón: DTO
 * No contiene lógica de negocio.
 */
public record AvailableSlotResponse(LocalTime start, LocalTime end) {}

