package apiTurnos.service.controller;
/**
 * Clase que comunica el Front con el Back
 * */

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
            var response = commandHandler.handle(command);
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
}
