package apiTurnos.appointment.mapper;

import apiTurnos.appointment.dto.response.AppointmentResponse;
import apiTurnos.appointment.model.Appointment;
import org.springframework.stereotype.Component;

/**
 * Mapper SRP: convierte entidad -> DTO de salida.
 * No contiene lógica de negocio, sólo transformación.
 */
@Component
public class AppointmentMapper {
    public AppointmentResponse toResponse(Appointment a) {
        return new AppointmentResponse(
                a.getId(),
                a.getUser().getId(),
                a.getBarber().getId(),
                a.getService().getId(),
                a.getDate(),
                a.getStartTime(),
                a.getEndTime(),
                a.getStatus()
        );
    }
}
