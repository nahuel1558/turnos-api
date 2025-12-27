package apiTurnos.appointment.model;

/**
 * Estados posibles de un turno.
 * CANCELLED se mantiene para conservar historial.
 */
public enum AppointmentStatus {
    BOOKED,
    CANCELLED
}
