package apiTurnos.appointment.presentation.dto.response;

import apiTurnos.appointment.domain.model.AppointmentStatus;

import java.time.LocalDate;
import java.time.LocalTime;
/**
 * Respuesta estándar de un turno.
 */
public record  AppointmentResponse (
        Long id,
        Long userId,
        Long barberId,
        Long serviceId,
        LocalDate date,
        LocalTime startTime,
        LocalTime endTime,
        AppointmentStatus status
) {}
