package apiTurnos.client.presentation.controller.command;

import apiTurnos.client.command.RegisterClientCommand;
import apiTurnos.client.command.RegisterClientHandler;
import apiTurnos.client.command.UpdateClientCommand;
import apiTurnos.client.command.UpdateClientHandler;
import apiTurnos.client.mapper.ClientMapper;
import apiTurnos.client.model.Client;
import apiTurnos.client.presentation.dto.request.RegisterClientRequest;
import apiTurnos.client.presentation.dto.request.UpdateClientRequest;
import apiTurnos.client.presentation.dto.response.ClientResponse;
import apiTurnos.common.dto.IdResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/clients")
@RequiredArgsConstructor
public class ClientCommandController {

    private final RegisterClientHandler registerClientHandler;
    private final UpdateClientHandler updateClientHandler;

    private final ClientMapper mapper = new ClientMapper();

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public IdResponse registerClient(@RequestBody RegisterClientRequest request) {

        RegisterClientCommand command = RegisterClientCommand.fromRequest(request);
        Client client = registerClientHandler.handle(command);

        return new IdResponse(client.getId());
    }

    @PutMapping("/{clientId}")
    public ClientResponse updateClient(
            @PathVariable Long clientId,
            @RequestBody UpdateClientRequest request
    ) {
        UpdateClientCommand command = UpdateClientCommand.fromRequest(clientId, request);
        Client updated = updateClientHandler.handle(command);
        return mapper.toResponse(updated);
    }
}
