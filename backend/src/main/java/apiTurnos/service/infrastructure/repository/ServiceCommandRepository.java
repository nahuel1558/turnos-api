package apiTurnos.service.infrastructure.repository;

import apiTurnos.service.domain.model.ServiceItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceCommandRepository extends JpaRepository<ServiceItem, Long> {

    // Metodo para buscar si existe el Servicio con el mismo nombre y active.
    boolean existsByNameAndActiveTrue(String name);

    // Metodo para buscar si existe el Servicio con el mismo nombre, id y desactivado.
    boolean existsByNameAndActiveTrueAndIdNot(String name, Long id);
}
