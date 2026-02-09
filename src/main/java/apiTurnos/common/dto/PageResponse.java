package apiTurnos.common.dto;

import lombok.*;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Respuesta genérica paginada.
 * Útil si después agregan búsqueda de turnos, barberos, usuarios, etc.
 */
@Builder
public record PageResponse<T>(
        List<T> items,
        int page,
        int size,
        long totalItems,
        int totalPages
) {
    /**
     * Factory method para crear una respuesta desde un Page de Spring
     */
    public static <T> PageResponse<T> fromPage(Page<T> page) {
        return PageResponse.<T>builder()
                .items(page.getContent())
                .page(page.getNumber())
                .size(page.getSize())
                .totalItems(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .build();
    }

    /**
     * Factory method para crear una respuesta vacía
     */
    public static <T> PageResponse<T> empty() {
        return PageResponse.<T>builder()
                .items(List.of())
                .page(0)
                .size(0)
                .totalItems(0)
                .totalPages(0)
                .build();
    }

    /**
     * Factory method para crear una respuesta desde una lista
     */
    public static <T> PageResponse<T> fromList(
            List<T> items,
            int page,
            int size,
            long totalItems) {

        int totalPages = (int) Math.ceil((double) totalItems / size);

        return PageResponse.<T>builder()
                .items(items)
                .page(page)
                .size(size)
                .totalItems(totalItems)
                .totalPages(totalPages)
                .build();
    }
}