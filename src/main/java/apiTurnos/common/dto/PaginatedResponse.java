package apiTurnos.common.dto;

import lombok.*;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * DTO estándar para respuestas paginadas.
 * Proporciona una estructura consistente para todas las respuestas paginadas.
 *
 * @param <T> Tipo de los elementos en la lista
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaginatedResponse<T> {

    // Datos de la página actual
    private List<T> content;

    // Metadata de paginación
    private int pageNumber;
    private int pageSize;
    private long totalElements;
    private int totalPages;

    // Información de navegación
    private boolean first;
    private boolean last;
    private boolean empty;

    /**
     * Factory method para crear una respuesta desde un Page de Spring
     */
    public static <T> PaginatedResponse<T> fromPage(Page<T> page) {
        return PaginatedResponse.<T>builder()
                .content(page.getContent())
                .pageNumber(page.getNumber())
                .pageSize(page.getSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .first(page.isFirst())
                .last(page.isLast())
                .empty(page.isEmpty())
                .build();
    }

    /**
     * Factory method para crear una respuesta vacía
     */
    public static <T> PaginatedResponse<T> empty() {
        return PaginatedResponse.<T>builder()
                .content(List.of())
                .pageNumber(0)
                .pageSize(0)
                .totalElements(0)
                .totalPages(0)
                .first(true)
                .last(true)
                .empty(true)
                .build();
    }

    /**
     * Factory method para crear una respuesta desde una lista
     * (útil cuando no se usa Spring Data directamente)
     */
    public static <T> PaginatedResponse<T> fromList(
            List<T> content,
            int pageNumber,
            int pageSize,
            long totalElements) {

        int totalPages = (int) Math.ceil((double) totalElements / pageSize);

        return PaginatedResponse.<T>builder()
                .content(content)
                .pageNumber(pageNumber)
                .pageSize(pageSize)
                .totalElements(totalElements)
                .totalPages(totalPages)
                .first(pageNumber == 0)
                .last(pageNumber >= totalPages - 1)
                .empty(content == null || content.isEmpty())
                .build();
    }
}