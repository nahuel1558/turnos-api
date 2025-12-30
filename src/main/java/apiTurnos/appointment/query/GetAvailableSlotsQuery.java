package apiTurnos.appointment.query;

import java.time.LocalDate;

/**
 * Query que representa la intención de consultar
 * los horarios disponibles para un peluquero y un servicio en una fecha.
 *
 * Patrón: Query (CQRS)
 * Responsabilidad única: transportar datos de entrada
 */
public record GetAvailableSlotsQuery(Long barberId, Long serviceId, LocalDate date) {}

