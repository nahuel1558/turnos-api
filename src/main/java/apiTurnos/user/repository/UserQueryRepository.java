package apiTurnos.user.repository;

import apiTurnos.user.model.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserQueryRepository extends JpaRepository<UserAccount, Long> {
    Optional<UserAccount> findByEmail(String email);
    List<UserAccount> findByEnabledTrue();

    @Query("SELECT u FROM UserAccount u JOIN u.roles r WHERE r.name = :roleName")
    List<UserAccount> findByRoleName(@Param("roleName") String roleName);

    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END " +
            "FROM UserAccount u JOIN u.roles r " +
            "WHERE u.id = :userId AND r.name = :roleName")
    boolean hasRole(@Param("userId") Long userId, @Param("roleName") String roleName);
}