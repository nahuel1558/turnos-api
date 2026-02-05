package apiTurnos.client.presentation.controller.command;

import apiTurnos.client.command.RegisterClientCommand;
import apiTurnos.client.command.RegisterClientHandler;
import apiTurnos.client.command.UpdateClientCommand;
import apiTurnos.client.command.UpdateClientHandler;
import apiTurnos.client.presentation.dto.request.RegisterClientRequest;
import apiTurnos.client.presentation.dto.request.UpdateClientRequest;
import apiTurnos.client.presentation.dto.response.ClientResponse;
import apiTurnos.client.mapper.ClientMapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/clients")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Client Commands", description = "Operaciones de creación y modificación de clientes")
public class ClientCommandController {

    private final RegisterClientHandler registerClientHandler;
    private final UpdateClientHandler updateClientHandler;
    private final ClientMapper clientMapper;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ClientResponse> createClientProfile(
            @Valid @RequestBody RegisterClientRequest request) {

        log.info("Creating client profile for user ID: {}", request.getUserId());

        RegisterClientCommand command = RegisterClientCommand.fromRequest(request);
        var client = registerClientHandler.handle(command);
        var response = clientMapper.toResponse(client);

        log.info("Client profile created successfully: {}", client.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{clientId}")
    @PreAuthorize("hasRole('CLIENT') or hasRole('ADMIN')")
    public ResponseEntity<ClientResponse> updateClient(
            @PathVariable Long clientId,
            @Valid @RequestBody UpdateClientRequest request) {

        log.info("Updating client with ID: {}", clientId);

        UpdateClientCommand command = UpdateClientCommand.fromUpdateRequest(clientId, request);
        var updatedClient = updateClientHandler.handle(command.getClientId(), command);
        var response = clientMapper.toResponse(updatedClient);

        log.info("Client updated successfully: {}", clientId);
        return ResponseEntity.ok(response);
    }


}