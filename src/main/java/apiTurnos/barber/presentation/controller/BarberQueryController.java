package apiTurnos.barber.presentation.controller;

import apiTurnos.barber.application.handler.BarberQueryHandler;
import apiTurnos.barber.application.query.*;
import apiTurnos.barber.domain.model.Barber;
import apiTurnos.barber.presentation.dto.response.BarberResponseDTO;
import apiTurnos.barber.presentation.dto.response.BarberSimpleResponseDTO;
import apiTurnos.common.dto.PaginatedRequest;
import apiTurnos.common.dto.PaginatedResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Future;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/barbers")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Barbers", description = "Operaciones de consulta de barberos")
public class BarberQueryController {

    private final BarberQueryHandler queryHandler;

    @GetMapping
    @Operation(summary = "Obtener todos los barberos",
            description = "Retorna una lista de barberos con filtros opcionales")
    public ResponseEntity<List<BarberSimpleResponseDTO>> getBarbers(
            @Parameter(description = "Término de búsqueda en nombre o bio")
            @RequestParam(required = false) String searchTerm,

            @Parameter(description = "Estado profesional del barbero")
            @RequestParam(required = false) Barber.ProfessionalStatus professionalStatus,

            @Parameter(description = "Especialidad específica")
            @RequestParam(required = false) String specialty,

            @Parameter(description = "Incluir barberos inactivos")
            @RequestParam(required = false, defaultValue = "false") Boolean includeInactive) {

        log.info("Getting barbers with filters - search: {}, status: {}, specialty: {}, includeInactive: {}",
                searchTerm, professionalStatus, specialty, includeInactive);

        GetBarbersQuery query = GetBarbersQuery.builder()
                .searchTerm(searchTerm)
                .professionalStatus(professionalStatus)
                .specialty(specialty)
                .includeInactive(includeInactive)
                .active(!includeInactive)
                .build();

        List<BarberSimpleResponseDTO> barbers = queryHandler.handle(query);
        return ResponseEntity.ok(barbers);
    }

    @GetMapping("/paginated")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Obtener barberos paginados",
            description = "Retorna barberos paginados para administración")
    public ResponseEntity<PaginatedResponse<BarberSimpleResponseDTO>> getBarbersPaginated(
            @Valid PaginatedRequest pagination,

            @Parameter(description = "Término de búsqueda en nombre o bio")
            @RequestParam(required = false) String searchTerm,

            @Parameter(description = "Especialidad específica")
            @RequestParam(required = false) String specialty,

            @Parameter(description = "Incluir barberos inactivos")
            @RequestParam(required = false, defaultValue = "false") Boolean includeInactive) {

        log.info("Getting paginated barbers - page: {}, size: {}, search: {}, specialty: {}",
                pagination.getPage(), pagination.getSize(), searchTerm, specialty);

        // Construir query específica para paginación
        GetBarbersPaginatedQuery query = GetBarbersPaginatedQuery.fromParams(
                pagination, searchTerm, specialty, !includeInactive);

        PaginatedResponse<BarberSimpleResponseDTO> response = queryHandler.handle(query);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{barberId}")
    @Operation(summary = "Obtener barbero por ID")
    public ResponseEntity<BarberResponseDTO> getBarberById(
            @PathVariable Long barberId) {

        log.info("Getting barber by ID: {}", barberId);

        GetBarberByIdQuery query = GetBarberByIdQuery.fromId(barberId);

        return queryHandler.handle(query)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/available")
    @Operation(summary = "Obtener barberos disponibles")
    public ResponseEntity<List<BarberSimpleResponseDTO>> getAvailableBarbers(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            @Future(message = "La fecha debe ser futura") LocalDateTime dateTime,

            @RequestParam(required = false) String specialty) {

        log.info("Getting available barbers for dateTime: {}, specialty: {}", dateTime, specialty);

        GetAvailableBarbersQuery query = GetAvailableBarbersQuery.builder()
                .dateTime(dateTime)
                .specialty(specialty)
                .build();

        List<BarberSimpleResponseDTO> availableBarbers = queryHandler.handle(query);
        return ResponseEntity.ok(availableBarbers);
    }

    @GetMapping("/user/{userId}")
    @PreAuthorize("hasRole('ADMIN') or #userId == authentication.principal.id")
    @Operation(summary = "Obtener barbero por ID de usuario")
    public ResponseEntity<BarberResponseDTO> getBarberByUserId(@PathVariable Long userId) {
        log.info("Getting barber by user ID: {}", userId);

        // TODO: Implementar caso de uso específico
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/specialties")
    @Operation(summary = "Obtener todas las especialidades")
    public ResponseEntity<List<String>> getAllSpecialties() {
        log.info("Getting all barber specialties");

        // TODO: Implementar servicio para especialidades únicas
        return ResponseEntity.ok(List.of("Corte", "Barba", "Corte + Barba"));
    }
}