package apiTurnos.appointment.dto.response;

import java.time.LocalTime;
/**
 * Respuesta de horarios disponibles.
 */
public record AvailableSlotResponse(LocalTime startTime) {
}
