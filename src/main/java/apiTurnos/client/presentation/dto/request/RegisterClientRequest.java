package apiTurnos.client.presentation.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request para crear un perfil de cliente para un usuario existente")
public class RegisterClientRequest {

    @NotNull(message = "User ID es requerido")
    @Schema(description = "ID del usuario existente que será cliente", example = "10")
    private String userId;

    @Schema(description = "Notas adicionales sobre el cliente",
            example = "Prefiere corte clásico", maxLength = 1000)
    private String notes;

    @Schema(description = "Alergias del cliente",
            example = "[\"Alergia a perfumes\", \"Pelo de gato\"]")
    private Set<String> allergies;

    @Schema(description = "ID del barbero preferido", example = "1")
    private Long preferredBarberId;

    @Schema(description = "Preferencias de notificación por email",
            example = "true", defaultValue = "true")
    @Builder.Default
    private Boolean prefersEmailNotifications = true;

    @Schema(description = "Preferencias de notificación por SMS",
            example = "false", defaultValue = "false")
    @Builder.Default
    private Boolean prefersSmsNotifications = false;
}