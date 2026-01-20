package apiTurnos.client.controller;

import apiTurnos.client.command.RegisterClientCommand;
import apiTurnos.client.command.RegisterClientHandler;
import apiTurnos.client.command.UpdateClientCommand;
import apiTurnos.client.command.UpdateClientHandler;
import apiTurnos.client.dto.response.ClientResponseDTO;
import apiTurnos.client.mapper.ClientMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/clients")
@RequiredArgsConstructor
public class ClientCommandController {

    private final RegisterClientHandler registerClientHandler;
    private final UpdateClientHandler updateClientHandler;
    private final ClientMapper clientMapper;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ClientResponseDTO> registerClient(
            @Valid @RequestBody RegisterClientCommand command) {

        var userAccount = registerClientHandler.handle(command);
        var client = clientMapper.toResponseDTOFromUser(userAccount);

        return ResponseEntity.status(HttpStatus.CREATED).body(client);
    }

    @PutMapping("/{clientId}")
    @PreAuthorize("hasRole('CLIENT') or hasRole('ADMIN')")
    public ResponseEntity<ClientResponseDTO> updateClient(
            @PathVariable Long clientId,
            @Valid @RequestBody UpdateClientCommand command) {

        var updatedClient = updateClientHandler.handle(clientId, command);
        var response = clientMapper.toResponseDTO(updatedClient);

        return ResponseEntity.ok(response);
    }
}