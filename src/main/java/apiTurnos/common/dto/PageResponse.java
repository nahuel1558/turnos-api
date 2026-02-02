package apiTurnos.common.dto;

import lombok.*;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Respuesta genérica paginada.
 * Útil si después agregan búsqueda de turnos, barberos, usuarios, etc.
 */
public record PageResponse<T>(
        List<T> items,
        int page,
        int size,
        long totalItems,
        int totalPages
) {}