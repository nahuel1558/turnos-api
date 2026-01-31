package apiTurnos.appointment.infrastructure.repository;

import apiTurnos.appointment.domain.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentCommandRepository extends JpaRepository<Appointment, Long> {
}
