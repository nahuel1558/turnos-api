package apiTurnos.appointment.infrastructure.mapper;

import apiTurnos.appointment.presentation.dto.response.AppointmentResponse;
import apiTurnos.appointment.domain.model.Appointment;
import org.springframework.stereotype.Component;

/**
 * Mapper SRP: convierte entidad -> DTO de salida.
 */
@Component
public class AppointmentMapper {

    public AppointmentResponse toResponse(Appointment a) {
        return new AppointmentResponse(
                a.getId(),
                a.getClient().getId(),                         // clientId
                a.getClient().getUserAccount().getId(),        // userId real
                a.getBarber().getId(),
                a.getService().getId(),
                a.getDate(),
                a.getStartTime(),
                a.getEndTime(),
                a.getStatus()
        );
    }
}

