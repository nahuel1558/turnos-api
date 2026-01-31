package apiTurnos.appointment.presentation.dto.response;

import java.time.LocalTime;

/**
 * Slot simple (para agenda o listados).
 */
public record AppointmentSlotResponse(LocalTime startTime, LocalTime endTime) {}

