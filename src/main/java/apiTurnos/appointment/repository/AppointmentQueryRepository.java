package apiTurnos.appointment.repository;

import apiTurnos.appointment.model.Appointment;
import apiTurnos.appointment.model.AppointmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface AppointmentQueryRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByBarber_IdAndDateAndStatus(Long barberId, LocalDate date, AppointmentStatus status);
}
