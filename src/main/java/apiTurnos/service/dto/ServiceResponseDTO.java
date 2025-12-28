package apiTurnos.service.dto;
/**
 * Clase utilizada para las respuestas/get del Service.
 * */

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor @NoArgsConstructor

public class ServiceResponseDTO {
    private Long idService;
    private String name;
    private String description;
    private Integer durationMinutes;
    private BigDecimal price;

    // Fecha y hora de cuando se creo este Service.
    private LocalDateTime createdAt;

    // Fecha y hora cuando se actualizo este Service.
    private LocalDateTime updateAt;

    // Variable que marca si esta activo o no el Service.
    private Boolean activate;
}
