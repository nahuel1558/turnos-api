package apiTurnos.appointment.query;

import java.time.LocalDate;
/**
 * Query: agenda del peluquero (turnos del día).
 */
public record GetBarberAgendaQuery(
        Long barberId,
        LocalDate date
) {}
