package apiTurnos.common.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * DTO genérico para respuestas paginadas.
 *
 * SRP: solo representa una página de resultados para la API.
 * Reutilizable en cualquier módulo (barber, client, appointment, etc).
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Respuesta paginada genérica")
public class PageResponse<T> {

    @Schema(description = "Contenido de la página")
    private List<T> content;

    @Schema(description = "Número de página actual (0-based)", example = "0")
    private int page;

    @Schema(description = "Tamaño de página", example = "10")
    private int size;

    @Schema(description = "Total de elementos", example = "57")
    private long totalElements;

    @Schema(description = "Total de páginas", example = "6")
    private int totalPages;

    @Schema(description = "¿Es la primera página?", example = "true")
    private boolean first;

    @Schema(description = "¿Es la última página?", example = "false")
    private boolean last;

    @Schema(description = "Cantidad de elementos en esta página", example = "10")
    private int numberOfElements;

    /**
     * Factory: construye un PageResponse a partir de un Page de Spring.
     *
     * OCP: se puede reutilizar con cualquier DTO sin modificar código.
     */
    public static <T> PageResponse<T> fromPage(Page<T> page) {
        return PageResponse.<T>builder()
                .content(page.getContent())
                .page(page.getNumber())
                .size(page.getSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .first(page.isFirst())
                .last(page.isLast())
                .numberOfElements(page.getNumberOfElements())
                .build();
    }
}
