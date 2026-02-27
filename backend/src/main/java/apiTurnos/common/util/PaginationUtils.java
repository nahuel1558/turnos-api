package apiTurnos.common.util;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Clase de utilidad con métodos helper para paginación
 */
public class PaginationUtils {

    /**
     * Aplica paginación manual a una lista
     */
    public static <T> Page<T> paginateList(List<T> items, Pageable pageable) {
        if (items == null || items.isEmpty()) {
            return Page.empty(pageable);
        }

        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), items.size());

        if (start > items.size()) {
            return new PageImpl<>(List.of(), pageable, items.size());
        }

        List<T> pageContent = items.subList(start, end);
        return new PageImpl<>(pageContent, pageable, items.size());
    }

    /**
     * Calcula el número total de páginas
     */
    public static int calculateTotalPages(long totalElements, int pageSize) {
        if (pageSize <= 0) return 0;
        return (int) Math.ceil((double) totalElements / pageSize);
    }

    /**
     * Valida parámetros de paginación
     */
    public static void validatePaginationParams(int page, int size) {
        if (page < 0) {
            throw new IllegalArgumentException("Page number must be >= 0");
        }
        if (size <= 0) {
            throw new IllegalArgumentException("Page size must be > 0");
        }
        if (size > 100) {
            throw new IllegalArgumentException("Page size cannot exceed 100");
        }
    }
}