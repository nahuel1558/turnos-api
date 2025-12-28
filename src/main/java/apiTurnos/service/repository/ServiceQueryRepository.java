package apiTurnos.service.repository;

import apiTurnos.service.model.ServiceItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface ServiceQueryRepository extends JpaRepository<ServiceItem, Long> {

    //Consultas especificas para lectura.

    //Listar todos los servicios activos.
    List<ServiceItem> findAllByActiveTrue();

    // Encontrar un servicio activo por ID.
    Optional<ServiceItem> findByIdAndActiveTrue(Long id);

    //Consulta especifica a la base de datos.
    @Query("SELECT s FROM services s WHERE s.active = true AND " +
    "LOWER(s.name) LIKE LOWER(CONCAT('%', :searchTerm, '%')) ")

    // Listar servicios activos en base a una busqueda.
    List<ServiceItem> searchActiveServices(String searchTerm);

    // Listar servicios con duracion menor a la especificada.
    List<ServiceItem> findByActiveTrueAndDurationMinutesLessThanEqual(Integer maxDuration);

    // Listar servicios entre el minimo elegido y el maximo elegido.
    List<ServiceItem> findByActiveTrueAndPriceBetween(BigDecimal minPrice, BigDecimal maxPrice);
}
