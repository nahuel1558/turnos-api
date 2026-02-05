package apiTurnos.client.repository;

import apiTurnos.client.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientCommandRepository extends JpaRepository<Client, Long> {

    boolean existsByUserAccountId(String id);
}
