package apiTurnos.common.cqrs;

/**
 * Marker interface para Commands (CQRS).
 * Un Command representa una operación que cambia estado.
 *
 * Ej: CreateAppointmentCommand, CancelAppointmentCommand
 */
public interface Command<R> {
    // Marker, sin métodos.
}