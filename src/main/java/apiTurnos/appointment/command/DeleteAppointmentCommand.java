package apiTurnos.appointment.command;
/**
 * Command: eliminar un turno (hard delete).
 * En sistemas reales, muchas veces se reemplaza por "cancelar" para auditoría.
 */
public record DeleteAppointmentCommand(Long appointmentId) {}
