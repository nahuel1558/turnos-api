package apiTurnos.service.presentation.controller.command;

import apiTurnos.service.application.command.CreateServiceCommand;
import apiTurnos.service.application.command.DeleteServiceCommand;
import apiTurnos.service.application.handler.ServiceCommandHandler;
import apiTurnos.service.application.command.UpdateServiceCommand;
import apiTurnos.service.presentation.dto.request.ServiceRequestDTO;
import apiTurnos.service.presentation.dto.response.ServiceResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/services/commands")
@RequiredArgsConstructor
public class ServiceCommandController {

    private final ServiceCommandHandler commandHandler;

    // Anotacion "POST"(Identifica el uso de "CREATE"). Metodo para "CREAR/CREATE" un nuevo servicio.
    @PostMapping
    public ResponseEntity<ServiceResponseDTO> createService(
            @Valid @RequestBody ServiceRequestDTO requestDTO){
        CreateServiceCommand command = CreateServiceCommand.fromRequest(requestDTO);
        ServiceResponseDTO response = commandHandler.handleCreate(command);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // Anotacion "PUT"(Identifica el uso de "UPDATE"). Metodo para "ACTUALIZAR/PUT" un servicio por "ID".
    @PutMapping("/{id}")
    public  ResponseEntity<ServiceResponseDTO> updateService(
            @PathVariable Long id,
            @Valid @RequestBody ServiceRequestDTO requestDTO){
        UpdateServiceCommand command = UpdateServiceCommand.fromRequest(id, requestDTO);
        ServiceResponseDTO response = commandHandler.handleUpdate(command);
        return ResponseEntity.ok(response);
    }

    // Anotacion "DELETE"(Identifica el uso de "DELETE"). Metodo para "BORRAR/DELETE" un servicio por "ID".
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteService(@PathVariable Long id){
        DeleteServiceCommand command = DeleteServiceCommand.builder()
                .idService(id)
                .build();
        commandHandler.handleDelete(command);
        return ResponseEntity.noContent().build();
    }

}
