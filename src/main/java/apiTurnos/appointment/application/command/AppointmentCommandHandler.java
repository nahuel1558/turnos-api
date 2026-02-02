package apiTurnos.appointment.application.command;

import apiTurnos.appointment.presentation.dto.response.AppointmentResponse;
import apiTurnos.appointment.infrastructure.mapper.AppointmentMapper;
import apiTurnos.appointment.domain.model.Appointment;
import apiTurnos.appointment.domain.model.AppointmentStatus;
import apiTurnos.appointment.infrastructure.repository.AppointmentCommandRepository;
import apiTurnos.appointment.infrastructure.repository.AppointmentQueryRepository;
import apiTurnos.barber.domain.model.Barber;
import apiTurnos.barber.infrastructure.repository.BarberQueryRepository;
import apiTurnos.common.exception.NotFoundException;
import apiTurnos.service.domain.model.ServiceItem;
import apiTurnos.service.infrastructure.repository.ServiceQueryRepository;
import apiTurnos.user.model.UserAccount;
import apiTurnos.user.repository.UserQueryRepository;
import lombok.RequiredArgsConstructor;

import org.apache.catalina.User;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;

/**
 * Handler CQRS (Commands): escribe/modifica estado.
 *
 * SRP:
 * - Orquesta repositorios y aplica reglas de negocio de "command side".
 * - No arma DTOs directamente: usa Mapper.
 *
 * DIP:
 * - Depende de abstracciones (repositories) y colabora con mapper.
 */
@Service
@RequiredArgsConstructor
public class AppointmentCommandHandler {
    private static final int STEP_MINUTES = 15;

    private final AppointmentCommandRepository appointmentCommandRepository;
    private final AppointmentQueryRepository appointmentQueryRepository;

    private final UserQueryRepository userQueryRepository;
    private final BarberQueryRepository barberQueryRepository;
    private final ServiceQueryRepository serviceQueryRepository;

    private final AppointmentMapper appointmentMapper;

    /**
     * CREATE:
     * - Valida existencia de user/barber/service
     * - Calcula endTime según duración del servicio
     * - Valida que el horario este dentro del horario laboral del barber
     * - Valida que no se solape con otros turnos BOOKED
     */

    public AppointmentResponse handle(CreateAppointmentCommand cmd) {
        UserAccount user = userQueryRepository.findById(cmd.userId())
                .orElseThrow(() -> new apiTurnos.common.exception.NotFoundException("Usuario no encontrado"));

        Barber barber = barberQueryRepository.findById(cmd.barberId())
                .orElseThrow(() -> new apiTurnos.common.exception.NotFoundException("Peluquero no encontrado"));

        ServiceItem service = serviceQueryRepository.findById(cmd.serviceId())
                .orElseThrow(() -> new apiTurnos.common.exception.NotFoundException("Servicio no encontrado"));

        int duration = service.getDurationMinutes();
        LocalTime start = cmd.startTime();
        LocalTime end = start.plusMinutes(duration);

        validateWithinWorkHours(barber, start, end);
        validateNoOverlap(barber.getId(), cmd.date(), start, end);

        Appointment appointment = Appointment.booked((User) user, barber, service, cmd.date(), start, end);
        Appointment saved = appointmentCommandRepository.save(appointment);

        return appointmentMapper.toResponse(saved);
    }

    /**
     * UPDATE:
     * - No permite modificar un turno cancelado
     * - Revalida solapamiento (excluyendo el turno actual)
     * - Recalcula endTime segun duracion del nuevo servicio
     */
    public AppointmentResponse handle(UpdateAppointmentCommand cmd) {
        Appointment appointment = appointmentCommandRepository.findById(cmd.appointmentId())
                .orElseThrow(()-> new apiTurnos.common.exception.NotFoundException("Turno no encontrado"));
        if (appointment.getStatus() == AppointmentStatus.CANCELED) {
            throw new IllegalStateException("No se puede modificar el turno cancelado");
        }

        ServiceItem newService = serviceQueryRepository.findById(cmd.serviceId())
                .orElseThrow(()-> new apiTurnos.common.exception.NotFoundException("Servicio no encontrado"));

        int duration = newService.getDurationMinutes();
        LocalTime newStart = cmd.startTime();
        LocalTime newEnd = newStart.plusMinutes(duration);

        validateWithinWorkHours(appointment.getBarber(), newStart, newEnd);
        validateNoOverlapExcluidingAppointment(
                appointment.getBarber().getId(),
                appointment.getDate(),
                newStart,
                newEnd,
                appointment.getId()
        );

        appointment.reschedule(newService, newStart, newEnd);
        Appointment saved = appointmentCommandRepository.save(appointment);

        return appointmentMapper.toResponse(saved);
    }

    /**
     * CANCEL:
     * - Cambia estado a CANCELED
     * - No borra para mantener historial
     */
    public AppointmentResponse handle(CancelAppointmentCommand cmd) {

        Appointment appointment = appointmentQueryRepository.findById(cmd.appointmentId())
                .orElseThrow(()-> new apiTurnos.common.exception.NotFoundException("Turno no encontrado"));

        appointment.cancel();
        Appointment saved = appointmentCommandRepository.save(appointment);

        return appointmentMapper.toResponse(saved);
    }

    /**
     * DELETE:
     * - Elimina definitivamente
     * - Útil para admin/testing, pero en produccion suele preferirse CNACEL
     */
    public void handle(DeleteAppointmentCommand cmd) {
        Appointment appointment = appointmentQueryRepository.findById(cmd.appointmentId())
                .orElseThrow(()-> new apiTurnos.common.exception.NotFoundException("Turno no encontrado"));

        appointmentCommandRepository.delete(appointment);
    }

    // -----------------------------
    // Helpers privados (SRP del handler)
    // -----------------------------

    private void validateWithinWorkHours(Barber barber, LocalTime start, LocalTime end) {
        if (start.isBefore(barber.getWorkStartTime()) || end.isAfter(barber.getWorkEndTime())) {
            throw new IllegalArgumentException("El horario esta fuera del horario laboral del peluquero");
        }
    }

    private void validateNoOverlap(Long barberId, java.time.LocalDate date, LocalTime start, LocalTime end) {
        List<Appointment> booked = appointmentQueryRepository
                .findByBarber_IdAndDateAndStatus(barberId, date, AppointmentStatus.BOOKED)
                .stream()
                .sorted(Comparator.comparing(Appointment::getStartTime))
                .toList();

        boolean overlaps = booked.stream().anyMatch(a -> overlaps(start, end, a.getStartTime(), a.getEndTime()));
        if (overlaps) {
            throw new IllegalArgumentException("El turno se solapa con otro turno existente");
        }
    }

    private void validateNoOverlapExcluidingAppointment(Long barberId, java.time.LocalDate date, LocalTime start, LocalTime end, Long excludeId) {
        List<Appointment> booked = appointmentQueryRepository
                .findByBarber_IdAndDateAndStatus(barberId, date, AppointmentStatus.BOOKED);

        boolean overlaps = booked.stream()
                .filter(a -> !a.getId().equals(excludeId))
                .anyMatch(a -> overlaps(start, end, a.getStartTime(), a.getEndTime()));

        if (overlaps) {
            throw new IllegalArgumentException("El turno se solapa con otro turno existente");
        }
    }

    private boolean overlaps(LocalTime aStart, LocalTime aEnd, LocalTime bStart, LocalTime bEnd) {
        // solapamiento: aStart < bEnd && aEnd > bStart
        return aStart.isBefore(bEnd) && aEnd.isAfter(bStart);
    }
}
