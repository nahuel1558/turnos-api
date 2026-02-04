package apiTurnos.client.presentation.controller.query;

import apiTurnos.client.presentation.dto.response.ClientResponse;
import apiTurnos.client.mapper.ClientMapper;
import apiTurnos.client.query.GetClientByIdQuery;
import apiTurnos.client.query.GetClientByUserIdQuery;
import apiTurnos.client.query.GetClientHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/clients")
@RequiredArgsConstructor
public class ClientQueryController {

    private final GetClientHandler getClientHandler;
    private final ClientMapper clientMapper;

    @GetMapping("/{clientId}")
    @PreAuthorize("hasRole('CLIENT') or hasRole('ADMIN') or hasRole('BARBER')")
    public ResponseEntity<ClientResponse> getClientById(@PathVariable Long clientId) {
        var query = new GetClientByIdQuery();
        query.setClientId(clientId);

        return getClientHandler.handle(query)
                .map(clientMapper::toResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/user/{userId}")
    @PreAuthorize("hasRole('CLIENT') or hasRole('ADMIN')")
    public ResponseEntity<ClientResponse> getClientByUserId(@PathVariable Long userId) {
        var query = new GetClientByUserIdQuery();
        query.setUserId(userId);

        return getClientHandler.handle(query)
                .map(clientMapper::toResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}