package apiTurnos.user.repository;

import apiTurnos.user.model.Role;
import apiTurnos.user.model.SystemRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SystemRoleRepository extends JpaRepository<SystemRole, Long> {
    Optional<SystemRole> findByRole(Role role);
}

