package apiTurnos.service.controller;
/*
  Clase que comunica el Front con el Back
  */

import apiTurnos.service.command.*;
import apiTurnos.service.dto.ServiceRequestDTO;
import apiTurnos.service.query.GetServiceByIdQuery;
import apiTurnos.service.query.GetServicesQuery;
import apiTurnos.service.query.ServiceQueryHandler;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/services")
@RequiredArgsConstructor
@CrossOrigin(origins = "*") // Modificar como queramos.
public class ServiceController {

    private final ServiceCommandHandler commandHandler;
    private final ServiceQueryHandler queryHandler;

    // CREAR
    @PostMapping
    public ResponseEntity<?> createService(@Valid @RequestBody ServiceRequestDTO requestDTO){
        try{
            CreateServiceCommand command = CreateServiceCommand.fromRequest(requestDTO);
            var response = commandHandler.handleCreate(command);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<?>> getAllServices(
            @RequestParam(required = false) Boolean includeInactive,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) Integer maxDuration,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice) {

        GetServicesQuery query = GetServicesQuery.builder()
                .includeInactive(includeInactive)
                .searchTerm(search)
                .maxDuration(maxDuration)
                .minPrice(minPrice)
                .maxPrice(maxPrice)
                .build();

        var services = queryHandler.handle(query);
        return ResponseEntity.ok(services);
    }

    // BUSCAR BY ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getServiceById(@PathVariable Long id){
        try{
            GetServiceByIdQuery query = GetServiceByIdQuery.builder()
                    .idService(id)
                    .build();
            var service = queryHandler.handleById(query);
            return ResponseEntity.ok(service);
        } catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body((e.getMessage()));
        }
    }

    // ACTUALIZAR
    @PutMapping("/{id}")
    public ResponseEntity<?> updateService(@PathVariable Long id, @Valid @RequestBody ServiceRequestDTO requestDTO){
        try {
            UpdateServiceCommand command = UpdateServiceCommand.fromRequest(id, requestDTO);
            var response = commandHandler.handleUpdate(command);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // BORRAR (soft delete)
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteService(@PathVariable Long id){
        try{
            DeleteServiceCommand command = DeleteServiceCommand.builder()
                    .idService(id)
                    .build();
            commandHandler.handleDelete(command);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e){
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

}
