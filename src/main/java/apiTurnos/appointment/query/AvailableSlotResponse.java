package apiTurnos.appointment.query;

import java.time.LocalTime;

/**
 * DTO de salida para horarios disponibles.
 * Se devuelve solo la hora de inicio.
 */
public record AvailableSlotResponse(LocalTime startTime) {
}
