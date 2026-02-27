package apiTurnos.appointment.domain.model;

/**
 * Estados del turno.
 * BOOKED  -> reservado/confirmado
 * CANCELED-> cancelado (no se elimina para auditoría)
 */
public enum AppointmentStatus {
    BOOKED,
    CANCELED
}
