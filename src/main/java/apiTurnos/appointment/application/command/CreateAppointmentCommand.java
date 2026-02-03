package apiTurnos.appointment.application.command;

import java.time.LocalDate;
import java.time.LocalTime;
/**
 * Command: crear un turno.
 *
 * SOLID:
 * - SRP: este record sólo transporta datos (no valida, no calcula, no consulta DB)
 * - OCP: si mañana agregamos "notes" o "paymentStatus", extendemos el command sin romper handler
 */
public record CreateAppointmentCommand(
        String userId,
        Long barberId,
        Long serviceId,
        LocalDate date,
        LocalTime startTime
) {}
