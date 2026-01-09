package apiTurnos.service.repository;

import apiTurnos.service.model.ServiceItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ServiceQueryRepository extends JpaRepository<ServiceItem, Long>, JpaSpecificationExecutor<ServiceItem> {

    //Spring Data generará automáticamente los métodos usando Specifications.

    // Encontrar un servicio activo por ID.
    Optional<ServiceItem> findByIdAndActiveTrue(Long id);
}
