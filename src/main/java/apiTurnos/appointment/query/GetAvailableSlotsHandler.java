package apiTurnos.appointment.query;

import apiTurnos.appointment.model.Appointment;
import apiTurnos.appointment.model.AppointmentStatus;
import apiTurnos.appointment.repository.AppointmentQueryRepository;
import apiTurnos.barber.model.Barber;
import apiTurnos.barber.repository.BarberQueryRepository;
import apiTurnos.service.model.ServiceItem;
import apiTurnos.service.repository.ServiceQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
    // Intervalo fijo entre posibles turnos (MVP)
    private static final int STEP_MINUTES = 15;

    private final BarberQueryRepository barberQueryRepository;
    private final ServiceQueryRepository serviceQueryRepository;
    private final AppointmentQueryRepository appointmentQueryRepository;

    public List<AvailableSlotResponse> handle(GetAvailableSlotsQuery query) {

        // Validación de existencia del peluquero
        Barber barber = barberQueryRepository.findById(query.barberId())
                .orElseThrow(() -> new IllegalArgumentException("Peluquero no encontrado"));

        // Validación de existencia del servicio
        ServiceItem service = serviceQueryRepository.findById(query.serviceId())
                .orElseThrow(() -> new IllegalArgumentException("Servicio no encontrado"));

        int duration = service.getDurationMinutes();

        // Obtiene turnos ya reservados
        List<Appointment> booked = appointmentQueryRepository
                .findByBarber_IdAndDateAndStatus(query.barberId(), query.date(), AppointmentStatus.BOOKED)
                .stream()
                .sorted(Comparator.comparing(Appointment::getStartTime))
                .toList();

        // Convierte los turnos en rangos horarios ocupados
        List<TimeRange> busyRanges = booked.stream()
                .map(a -> new TimeRange(a.getStartTime(), a.getEndTime()))
                .toList();

        LocalTime start = barber.getWorkStart();
        LocalTime end = barber.getWorkEnd();

        List<AvailableSlotResponse> slots = new ArrayList<>();

        LocalTime cursor = start;

        // Recorre la jornada laboral en pasos de 15 minutos
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

    /**
     * Rango horario auxiliar para validar solapamientos.
     */
    private record TimeRange(LocalTime start, LocalTime end) {
        boolean overlaps(LocalTime aStart, LocalTime aEnd) {
            // solapamiento: aStart < end && aEnd > start
            return aStart.isBefore(end) && aEnd.isAfter(start);
        }
    }
}
