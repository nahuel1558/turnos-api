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
        log.debug("Processing GetClientByIdQuery: {}", query.getClientId());
        return clientRepository.findById(query.getClientId());
    }

    public Optional<Client> handle(GetClientByUserIdQuery query) {
        log.debug("Processing GetClientByUserIdQuery: {}", query.getUserId());
        return clientRepository.findByUserAccountId(query.getUserId());
    }

    public boolean existsByUserId(Long userId) {
        return clientRepository.existsByUserAccountId(userId);
    }
}