package apiTurnos.barber.infrastructure.repository;

import apiTurnos.barber.domain.model.Barber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface BarberCommandRepository extends JpaRepository<Barber, Long> {


    boolean existsByUserAccountId(String userId);

    boolean existsByDisplayNameAndActiveTrue(String displayName);

    @Query("SELECT COUNT(b) > 0 FROM Barber b " +
            "WHERE b.displayName = :displayName " +
            "AND b.id != :barberId " +
            "AND b.active = true")
    boolean existsByDisplayNameAndIdNotActiveTrue(
            @Param("displayName") String displayName,
            @Param("barberId") Long barberId);

    @Transactional
    @Modifying
    @Query("UPDATE Barber b SET b.professionalStatus = :status WHERE b.id = :id")
    void updateProfessionalStatus(@Param("id") Long id,
                                  @Param("status") Barber.ProfessionalStatus status);

    @Transactional
    @Modifying
    @Query("UPDATE Barber b SET b.active = :active WHERE b.id = :id")
    void updateActiveStatus(@Param("id") Long id, @Param("active") Boolean active);
}