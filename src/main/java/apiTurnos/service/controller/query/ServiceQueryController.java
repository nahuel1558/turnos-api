package apiTurnos.service.controller.query;

import apiTurnos.service.query.*;
import apiTurnos.service.dto.ServiceResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/services/queries")
@RequiredArgsConstructor
public class ServiceQueryController {

    private final ServiceQueryHandler queryHandler;

    // Anotacion "GET"(Identifica el uso de "GET"). Metodo para "TRAER/GET" todos los servicios (con filtros de busqueda).
    @GetMapping
    public ResponseEntity<List<ServiceResponseDTO>> getAllServices(
            @RequestParam(required = false) Boolean includeInactive,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) Integer maxDuration,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice){
        GetServicesQuery query = GetServicesQuery.builder()
                .includeInactive(includeInactive)
                .searchTerm(search)
                .maxDuration(maxDuration)
                .minPrice(minPrice)
                .maxPrice(maxPrice)
                .build();

        List<ServiceResponseDTO> services = queryHandler.handleGetAllServices(query);
        return ResponseEntity.ok(services);
    }

    // Anotacion "GET"(Identifica el uso de "GET"). Metodo para "TRAER/GET" un servicio por "ID".
    @GetMapping("/{id}")
    public ResponseEntity<ServiceResponseDTO> getServiceById(@PathVariable Long id){
        GetServiceByIdQuery query = GetServiceByIdQuery.builder()
                .idService(id)
                .build();
        ServiceResponseDTO service = queryHandler.handleById(query);
        return ResponseEntity.ok(service);
    }
}
