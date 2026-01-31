package apiTurnos.appointment.query;

import apiTurnos.appointment.model.Appointment;
import apiTurnos.appointment.model.AppointmentStatus;
import apiTurnos.appointment.repository.AppointmentQueryRepository;
import apiTurnos.barber.domain.model.Barber;
import apiTurnos.barber.infrastructure.repository.BarberQueryRepository;
import apiTurnos.service.domain.model.ServiceItem;
import apiTurnos.service.infrastructure.repository.ServiceQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import apiTurnos.common.exception.NotFoundException;


import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Handler CQRS encargado de calcular los horarios disponibles.
 *
 * No modifica estado, solo consulta y calcula.
 */
@Service
@RequiredArgsConstructor
public class GetAvailableSlotsHandler {

    private static final int STEP_MINUTES = 15;

    private final BarberQueryRepository barberQueryRepository;
    private final ServiceQueryRepository serviceQueryRepository;
    private final AppointmentQueryRepository appointmentQueryRepository;

    public List<AvailableSlotResponse> handle(GetAvailableSlotsQuery query) {

        Barber barber = barberQueryRepository.findById(query.barberId())
                .orElseThrow(() -> new NotFoundException("Peluquero no encontrado"));

        ServiceItem service = serviceQueryRepository.findById(query.serviceId())
                .orElseThrow(() -> new NotFoundException("Servicio no encontrado"));

        int duration = service.getDurationMinutes();

        List<Appointment> booked = appointmentQueryRepository
                .findByBarber_IdAndDateAndStatus(query.barberId(), query.date(), AppointmentStatus.BOOKED)
                .stream()
                .sorted(Comparator.comparing(Appointment::getStartTime))
                .toList();

        List<TimeRange> busyRanges = booked.stream()
                .map(a -> new TimeRange(a.getStartTime(), a.getEndTime()))
                .toList();

        LocalTime start = barber.getWorkStart();
        LocalTime end = barber.getWorkEnd();

        List<AvailableSlotResponse> slots = new ArrayList<>();
        LocalTime cursor = start;

        while (!cursor.plusMinutes(duration).isAfter(end)) {
            LocalTime candidateStart = cursor;
            LocalTime candidateEnd = cursor.plusMinutes(duration);

            boolean overlaps = busyRanges.stream().anyMatch(r -> r.overlaps(candidateStart, candidateEnd));
            if (!overlaps) {
                slots.add(new AvailableSlotResponse(candidateStart));
            }

            cursor = cursor.plusMinutes(STEP_MINUTES);
        }

        return slots;
    }

    private record TimeRange(LocalTime start, LocalTime end) {
        boolean overlaps(LocalTime aStart, LocalTime aEnd) {
            return aStart.isBefore(end) && aEnd.isAfter(start);
        }
    }
}