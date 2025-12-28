package apiTurnos.service.repository;

import apiTurnos.service.model.ServiceItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceCommandRepository extends JpaRepository<ServiceItem, Long> {

    // Metodo para buscar si existe el Servicio con el mismo nombre y activo.
    boolean existByNameAndActiveTrue(String name);

    // Metodo para buscar si existe el Servicio con el mismo nombre, id y desactivado.
    boolean existByNameAndIdNotActiveTrue(String name, Long id);
}
