package apiTurnos.appointment.presentation.dto.request;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Request del endpoint de creación.
 * MVP: recibimos userId para poder ubicar su Client.
 */
public record CreateAppointmentRequest(
        @NotNull String userId,
        @NotNull Long barberId,
        @NotNull Long serviceId,
        @NotNull LocalDate date,
        @NotNull LocalTime startTime
) {}

