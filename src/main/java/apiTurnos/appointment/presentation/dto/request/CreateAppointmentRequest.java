package apiTurnos.appointment.presentation.dto.request;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Request del endpoint de creación.
 * Nota: userId lo podés sacar del JWT en el futuro.
 * En MVP lo recibimos para probar rápido.
 */
public record  CreateAppointmentRequest (
        @NotNull Long userId,
        @NotNull Long barberId,
        @NotNull Long serviceId,
        @NotNull LocalDate date,
        @NotNull LocalTime startTime
) {}
