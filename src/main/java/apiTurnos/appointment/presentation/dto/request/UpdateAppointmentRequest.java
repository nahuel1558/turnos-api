package apiTurnos.appointment.dto.request;

import jakarta.validation.constraints.NotNull;

import java.time.LocalTime;
/**
 * Request de actualización: permite cambiar servicio y horario.
 * date no se cambia acá.
 */
public record UpdateAppointmentRequest(
        @NotNull Long serviceId,
        @NotNull LocalTime startTime
) {
}
