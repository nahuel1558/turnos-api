package apiTurnos.barber.infrastructure.repository;

import apiTurnos.barber.domain.model.Barber;
import apiTurnos.barber.infrastructure.specification.BarberSpecification;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BarberQueryRepository extends JpaRepository<Barber, Long>,
        JpaSpecificationExecutor<Barber> {

    Optional<Barber> findByUserAccountId(Long userId);

    Optional<Barber> findByIdAndActiveTrue(Long id);

    List<Barber> findByActiveTrue();

    List<Barber> findByProfessionalStatus(Barber.ProfessionalStatus status);

    @Query("SELECT b FROM Barber b WHERE b.active = true " +
            "AND b.professionalStatus = 'AVAILABLE' " +
            "AND :specialty MEMBER OF b.specialties")
    List<Barber> findAvailableBySpecialty(@Param("specialty") String specialty);

    @Query("SELECT b FROM Barber b WHERE b.active = true " +
            "AND b.professionalStatus = 'AVAILABLE' " +
            "ORDER BY b.displayName")
    List<Barber> findAllAvailable();

    @Query("SELECT b FROM Barber b JOIN b.specialties s " +
            "WHERE b.active = true " +
            "AND LOWER(b.displayName) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
            "OR LOWER(s) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    List<Barber> searchByDisplayNameOrSpecialty(@Param("searchTerm") String searchTerm);

    List<Barber> findAll(BarberSpecification spec, Sort sort);
}