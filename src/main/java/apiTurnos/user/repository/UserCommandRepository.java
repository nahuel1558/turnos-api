package apiTurnos.user.repository;

import apiTurnos.user.model.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface UserCommandRepository extends JpaRepository<UserAccount, Long> {
    Optional<UserAccount> findByEmail(String email);
    boolean existsByEmail(String email);

    @Transactional
    @Modifying
    @Query("UPDATE UserAccount u SET u.lastLogin = :lastLogin WHERE u.id = :id")
    void updateLastLogin(@Param("id") Long id, @Param("lastLogin") LocalDateTime lastLogin);

    @Transactional
    @Modifying
    @Query("UPDATE UserAccount u SET u.enabled = :enabled WHERE u.id = :id")
    void updateEnabledStatus(@Param("id") Long id, @Param("enabled") boolean enabled);
}