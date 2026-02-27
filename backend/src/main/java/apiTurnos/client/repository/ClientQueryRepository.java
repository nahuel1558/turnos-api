package apiTurnos.client.repository;

import apiTurnos.client.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientQueryRepository extends JpaRepository<Client, Long> {

    Optional<Client> findByUserAccount_Id(String userId);

    boolean existsByUserAccount_Id(String userId);
}


