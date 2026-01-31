package apiTurnos.appointment.repository;

import apiTurnos.appointment.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentCommandRepository extends JpaRepository<Appointment, Long> {
}
