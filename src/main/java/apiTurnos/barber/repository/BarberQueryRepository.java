package apiTurnos.barber.repository;

import apiTurnos.barber.model.Barber;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BarberQueryRepository extends JpaRepository<Barber, Long> {
}
