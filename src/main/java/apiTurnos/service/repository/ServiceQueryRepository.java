package apiTurnos.service.repository;

import apiTurnos.service.model.ServiceItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceQueryRepository extends JpaRepository<ServiceItem, Long> {
}
