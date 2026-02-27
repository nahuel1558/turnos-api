package apiTurnos.client.query;

import apiTurnos.client.model.Client;
import apiTurnos.client.repository.ClientQueryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class GetClientHandler {

    private final ClientQueryRepository clientRepository;

    public Optional<Client> handle(GetClientByIdQuery query) {
        log.debug("GetClientByIdQuery: {}", query.clientId());
        return clientRepository.findById(query.clientId());
    }

    public Optional<Client> handle(GetClientByUserIdQuery query) {
        log.debug("GetClientByUserIdQuery: {}", query.userId());
        return clientRepository.findByUserAccount_Id(query.userId());
    }

    public boolean existsByUserId(String userId) {
        return clientRepository.existsByUserAccount_Id(userId);
    }
}

