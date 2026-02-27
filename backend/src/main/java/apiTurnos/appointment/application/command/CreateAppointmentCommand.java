package apiTurnos.appointment.application.command;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Command: crear un turno.
 * SRP: solo transporta datos.
 */
public record CreateAppointmentCommand(
        String userId,
        Long barberId,
        Long serviceId,
        LocalDate date,
        LocalTime startTime
) {}

