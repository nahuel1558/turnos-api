package apiTurnos.user.repository;

import apiTurnos.user.model.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repositorio para operaciones de escritura (Commands).
 */
public interface UserCommandRepository extends JpaRepository<UserAccount, String> {
}
