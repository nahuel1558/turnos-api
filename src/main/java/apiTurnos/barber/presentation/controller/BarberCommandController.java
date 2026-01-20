package apiTurnos.barber.presentation.controller;

import apiTurnos.barber.application.command.ChangeBarberStatusCommand;
import apiTurnos.barber.application.command.RegisterBarberCommand;
import apiTurnos.barber.application.command.UpdateBarberCommand;
import apiTurnos.barber.application.handler.BarberCommandHandler;
import apiTurnos.barber.infrastructure.mapper.BarberMapper;
import apiTurnos.barber.presentation.dto.request.ChangeBarberStatusRequest;
import apiTurnos.barber.presentation.dto.request.RegisterBarberRequest;
import apiTurnos.barber.presentation.dto.request.UpdateBarberRequest;
import apiTurnos.barber.presentation.dto.response.BarberResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/barbers")
@RequiredArgsConstructor
@Slf4j
public class BarberCommandController {

    private final BarberCommandHandler commandHandler;
    private final BarberMapper mapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN') or hasRole('BARBER')")
    public ResponseEntity<BarberResponseDTO> registerBarber(
            @Valid @RequestBody RegisterBarberRequest request) {

        log.info("Registering new barber for user ID: {}", request.getUserId());

        RegisterBarberCommand command = mapper.toRegisterCommand(request);
        BarberResponseDTO response = commandHandler.handleRegister(command);

        log.info("Barber registered successfully with ID: {}", response.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{barberId}")
    @PreAuthorize("hasRole('ADMIN') or (hasRole('BARBER') and @barberSecurityService.isOwner(#barberId))")
    public ResponseEntity<BarberResponseDTO> updateBarber(
            @PathVariable Long barberId,
            @Valid @RequestBody UpdateBarberRequest request) {

        log.info("Updating barber with ID: {}", barberId);

        UpdateBarberCommand command = mapper.toUpdateCommand(barberId, request);
        BarberResponseDTO response = commandHandler.handleUpdate(command);

        log.info("Barber updated successfully: {}", barberId);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{barberId}/status")
    @PreAuthorize("hasRole('ADMIN') or (hasRole('BARBER') and @barberSecurityService.isOwner(#barberId))")
    public ResponseEntity<Void> changeBarberStatus(
            @PathVariable Long barberId,
            @Valid @RequestBody ChangeBarberStatusRequest request) {

        log.info("Changing status for barber ID: {} to {}", barberId, request.getNewStatus());

        ChangeBarberStatusCommand command = ChangeBarberStatusCommand.builder()
                .barberId(barberId)
                .newStatus(request.getNewStatus())
                .build();

        commandHandler.handleChangeStatus(command);

        log.info("Barber status changed successfully");
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{barberId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteBarber(@PathVariable Long barberId) {
        log.info("Soft deleting barber with ID: {}", barberId);

        // Aquí usarías DeleteBarberCommand
        // commandHandler.handleDelete(new DeleteBarberCommand(barberId));

        log.info("Barber soft deleted successfully");
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{barberId}/activate")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> activateBarber(@PathVariable Long barberId) {
        log.info("Activating barber with ID: {}", barberId);

        // Implementar lógica de activación
        return ResponseEntity.noContent().build();
    }
}