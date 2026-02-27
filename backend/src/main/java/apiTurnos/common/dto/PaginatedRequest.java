package apiTurnos.common.dto;

import lombok.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

/**
 * DTO estándar para solicitudes paginadas.
 * Se puede usar en cualquier endpoint que necesite paginación.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaginatedRequest {

    @Min(value = 0, message = "El número de página debe ser mayor o igual a 0")
    @Builder.Default
    private int page = 0;

    @Min(value = 1, message = "El tamaño de página debe ser mayor o igual a 1")
    @Max(value = 100, message = "El tamaño de página no puede exceder 100")
    @Builder.Default
    private int size = 20;

    private String sortBy;

    @Builder.Default
    private Sort.Direction direction = Sort.Direction.ASC;

    /**
     * Convierte este DTO a un Pageable de Spring
     */
    public Pageable toPageable() {
        if (sortBy != null && !sortBy.trim().isEmpty()) {
            Sort sort = Sort.by(direction, sortBy);
            return PageRequest.of(page, size, sort);
        }
        return PageRequest.of(page, size);
    }

    /**
     * Convierte este DTO a un Pageable de Spring con ordenación por defecto
     */
    public Pageable toPageable(String defaultSortBy) {
        String sortField = (sortBy != null && !sortBy.trim().isEmpty()) ? sortBy : defaultSortBy;
        Sort sort = Sort.by(direction, sortField);
        return PageRequest.of(page, size, sort);
    }
}