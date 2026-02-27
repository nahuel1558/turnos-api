package apiTurnos.appointment.infrastructure.repository;

import apiTurnos.appointment.domain.model.Appointment;
import apiTurnos.appointment.domain.model.AppointmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface AppointmentQueryRepository extends JpaRepository<Appointment, Long> {

    // Agenda del barbero en un día (con/sin status)
    List<Appointment> findByBarber_IdAndDateOrderByStartTimeAsc(Long barberId, LocalDate date);

    List<Appointment> findByBarber_IdAndDateAndStatusOrderByStartTimeAsc(
            Long barberId, LocalDate date, AppointmentStatus status
    );

    // Turnos del cliente por rango de fechas (ordenado)
    List<Appointment> findByClient_IdAndDateBetweenOrderByDateAscStartTimeAsc(
            Long clientId, LocalDate from, LocalDate to
    );
}

