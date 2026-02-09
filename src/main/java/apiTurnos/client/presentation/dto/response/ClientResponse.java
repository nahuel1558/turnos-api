package apiTurnos.client.presentation.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Respuesta completa con datos de cliente y usuario")
public class ClientResponse {

    @Schema(description = "ID del cliente", example = "1")
    private Long id;

    @Schema(description = "ID del usuario asociado", example = "f47ac10b-58cc-4372-a567-0e02b2c3d479")
    private String userId;

    @Schema(description = "Email del cliente", example = "cliente@ejemplo.com")
    private String email;

    @Schema(description = "Nombre completo del cliente", example = "Juan Pérez")
    private String fullName;

    @Schema(description = "Nombre", example = "Juan")
    private String firstName;

    @Schema(description = "Apellido", example = "Pérez")
    private String lastName;

    @Schema(description = "Notas adicionales", example = "Prefiere corte clásico")
    private String notes;

    @Schema(description = "Alergias del cliente", example = "[\"Alergia a perfumes\"]")
    private Set<String> allergies;

    @Schema(description = "ID del barbero preferido", example = "1")
    private Long preferredBarberId;

    @Schema(description = "Total de turnos atendidos", example = "15")
    private Integer totalAppointments;

    @Schema(description = "Prefiere notificaciones por email", example = "true")
    private Boolean prefersEmailNotifications;

    @Schema(description = "Prefiere notificaciones por SMS", example = "false")
    private Boolean prefersSmsNotifications;

    @Schema(description = "Cliente activo", example = "true")
    private Boolean active;

    @Schema(description = "Fecha de creación del cliente")
    private LocalDateTime clientSince;

    @Schema(description = "Fecha de última actualización")
    private LocalDateTime updatedAt;

    @Schema(description = "Estado del usuario", example = "ACTIVE")
    private String userStatus;
}
