package apiTurnos.client.presentation.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request para actualizar datos específicos del cliente")
public class UpdateClientRequest {

    @Schema(description = "Notas adicionales sobre el cliente",
            example = "Prefiere corte clásico", maxLength = 1000)
    private String notes;

    @Schema(description = "Alergias del cliente",
            example = "[\"Alergia a perfumes\", \"Pelo de gato\"]")
    private Set<String> allergies;

    @Schema(description = "ID del barbero preferido", example = "1")
    private Long preferredBarberId;

    @Schema(description = "Preferencias de notificación por email", example = "true")
    private Boolean prefersEmailNotifications;

    @Schema(description = "Preferencias de notificación por SMS", example = "true")
    private Boolean prefersSmsNotifications;
}