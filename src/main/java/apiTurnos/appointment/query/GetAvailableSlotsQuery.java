package apiTurnos.appointment.query;

import java.time.LocalDate;

public record GetAvailableSlotsQuery(
        Long barberId,
        Long serviceId,
        LocalDate date
) {
}
