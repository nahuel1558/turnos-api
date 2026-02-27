package apiTurnos.appointment.application.query;

import apiTurnos.appointment.domain.model.Appointment;
import apiTurnos.appointment.infrastructure.mapper.AppointmentMapper;
import apiTurnos.appointment.infrastructure.repository.AppointmentQueryRepository;
import apiTurnos.appointment.presentation.dto.response.AppointmentResponse;
import apiTurnos.client.repository.ClientQueryRepository;
import apiTurnos.common.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GetClientAppointmentsHandler {

    private final ClientQueryRepository clientQueryRepository;
    private final AppointmentQueryRepository appointmentQueryRepository;
    private final AppointmentMapper appointmentMapper;

    public List<AppointmentResponse> handle(GetClientAppointmentsQuery query) {

        // Validación: cliente existe
        clientQueryRepository.findById(query.clientId())
                .orElseThrow(() -> new NotFoundException("Cliente no encontrado"));

        // Regla simple: rango válido
        if (query.from().isAfter(query.to())) {
            throw new IllegalArgumentException("El rango de fechas es inválido (from > to)");
        }

        List<Appointment> appointments = appointmentQueryRepository
                .findByClient_IdAndDateBetweenOrderByDateAscStartTimeAsc(
                        query.clientId(), query.from(), query.to()
                );

        return appointments.stream()
                .map(appointmentMapper::toResponse)
                .toList();
    }
}

