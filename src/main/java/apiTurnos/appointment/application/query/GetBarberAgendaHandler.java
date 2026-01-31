package apiTurnos.appointment.application.query;

import apiTurnos.appointment.presentation.dto.response.AppointmentSlotResponse;
import apiTurnos.appointment.infrastructure.repository.AppointmentQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

/**
 * Handler de lectura: devuelve los slots ocupados del barber en un día.
 */
@Service
@RequiredArgsConstructor
public class GetBarberAgendaHandler {
    private final AppointmentQueryRepository appointmentQueryRepository;

    public List<AppointmentSlotResponse> handle(GetBarberAgendaQuery query) {
        return appointmentQueryRepository.findByBarber_IdAndDate(query.barberId(), query.date())
                .stream()
                .sorted(Comparator.comparing(a -> a.getStartTime()))
                .map(a -> new AppointmentSlotResponse(a.getStartTime(), a.getEndTime()))
                .toList();
    }
}
