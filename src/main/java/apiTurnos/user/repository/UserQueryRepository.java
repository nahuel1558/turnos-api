package apiTurnos.user.repository;

import apiTurnos.user.model.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repositorio para operaciones de lectura (Queries).
 */
public interface UserQueryRepository extends JpaRepository<UserAccount, String> {
    Optional<UserAccount> findByEmail(String email);
}
