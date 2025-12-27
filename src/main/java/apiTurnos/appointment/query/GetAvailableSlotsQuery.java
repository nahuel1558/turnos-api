package apiTurnos.appointment.query;

import java.time.LocalDate;
/**
 * Query que representa la intención de consultar
 * los horarios disponibles para un peluquero.
 */
public record GetAvailableSlotsQuery(
        Long barberId,
        Long serviceId,
        LocalDate date
) {
}
