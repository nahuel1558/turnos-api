package apiTurnos.appointment.command;

import java.time.LocalTime;
/**
 * Command: actualizar el turno (servicio + horario).
 */
public record UpdateAppointmentCommand(
        Long appointmentId,
        Long serviceId,
        LocalTime startTime
) {}
