package apiTurnos.appointment.application.query;

import java.time.LocalDate;

public record GetClientAppointmentsQuery(
        Long clientId,
        LocalDate from,
        LocalDate to
) {}

