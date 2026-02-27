package apiTurnos.appointment.presentation.dto.response;

import apiTurnos.appointment.domain.model.AppointmentStatus;

import java.time.LocalTime;

public record BarberAgendaItemResponse(
        Long appointmentId,
        LocalTime startTime,
        LocalTime endTime,
        AppointmentStatus status,
        Long clientId,
        String clientName,
        Long serviceId,
        String serviceName
) {}

