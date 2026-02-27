package apiTurnos.client.presentation.controller.query;

import apiTurnos.client.mapper.ClientMapper;
import apiTurnos.client.model.Client;
import apiTurnos.client.presentation.dto.response.ClientResponse;
import apiTurnos.client.query.GetClientByIdQuery;
import apiTurnos.client.query.GetClientByUserIdQuery;
import apiTurnos.client.query.GetClientHandler;
import apiTurnos.common.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/clients")
@RequiredArgsConstructor
public class ClientQueryController {

    private final GetClientHandler getClientHandler;
    private final ClientMapper mapper = new ClientMapper();

    @GetMapping("/{clientId}")
    public ClientResponse getById(@PathVariable Long clientId) {

        Client client = getClientHandler.handle(new GetClientByIdQuery(clientId))
                .orElseThrow(() -> new NotFoundException("Cliente no encontrado"));

        return mapper.toResponse(client);
    }

    @GetMapping("/by-user/{userId}")
    public ClientResponse getByUserId(@PathVariable String userId) {

        Client client = getClientHandler.handle(new GetClientByUserIdQuery(userId))
                .orElseThrow(() -> new NotFoundException("Cliente no encontrado para el usuario"));

        return mapper.toResponse(client);
    }
}
