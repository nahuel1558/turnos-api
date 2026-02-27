package apiTurnos.appointment.application.query;

import apiTurnos.appointment.domain.model.Appointment;
import apiTurnos.appointment.infrastructure.repository.AppointmentQueryRepository;
import apiTurnos.appointment.presentation.dto.response.BarberAgendaItemResponse;
import apiTurnos.barber.infrastructure.repository.BarberQueryRepository;
import apiTurnos.common.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GetBarberAgendaHandler {

    private final BarberQueryRepository barberQueryRepository;
    private final AppointmentQueryRepository appointmentQueryRepository;

    public List<BarberAgendaItemResponse> handle(GetBarberAgendaQuery query) {

        // Validación: barbero existe
        barberQueryRepository.findById(query.barberId())
                .orElseThrow(() -> new NotFoundException("Peluquero no encontrado"));

        List<Appointment> agenda = appointmentQueryRepository
                .findByBarber_IdAndDateOrderByStartTimeAsc(query.barberId(), query.date());

        return agenda.stream()
                .map(a -> new BarberAgendaItemResponse(
                        a.getId(),
                        a.getStartTime(),
                        a.getEndTime(),
                        a.getStatus(),
                        a.getClient().getId(),
                        a.getClient().getFullName(),
                        a.getService().getId(),
                        a.getService().getName()
                ))
                .toList();
    }
}

