package apiTurnos.service.presentation.controller.query;

import apiTurnos.service.application.query.GetServiceByIdQuery;
import apiTurnos.service.application.query.GetServicesQuery;
import apiTurnos.service.application.handler.ServiceQueryHandler;
import apiTurnos.service.presentation.dto.response.ServiceResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

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

        List<ServiceResponseDTO> services = queryHandler.handle(query);
        return ResponseEntity.ok(services);
    }

    // Anotacion "GET"(Identifica el uso de "GET"). Metodo para "TRAER/GET" un servicio por "ID".
    @GetMapping("/{id}")
    public ResponseEntity<ServiceResponseDTO> getServiceById(@PathVariable Long id){
        GetServiceByIdQuery query = GetServiceByIdQuery.builder()
                .idService(id)
                .build();
        Optional<ServiceResponseDTO> service = queryHandler.handle(query);
        return service.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
