package apiTurnos.appointment.command;
/**
 * Command: cancelar un turno.
 * Mantiene el registro y cambia el estado a CANCELED.
 */
public record CancelAppointmentCommand(Long appointmentId) {}
