package apiTurnos.appointment.repository;

import apiTurnos.appointment.model.Appointment;
import apiTurnos.appointment.model.AppointmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

/**
 * Repositorio de lectura para turnos.
 * Utilizado exclusivamente por Queries (CQRS).
 */
public interface AppointmentQueryRepository extends JpaRepository<Appointment, Long> {
    /**
     * Obtiene los turnos reservados de un peluquero en una fecha específica.
     */
    List<Appointment> findByBarber_IdAndDateAndStatus(Long barberId, LocalDate date, AppointmentStatus status);

    List<Appointment> findByBarber_IdAndDate(Long barberId, LocalDate date);
}
