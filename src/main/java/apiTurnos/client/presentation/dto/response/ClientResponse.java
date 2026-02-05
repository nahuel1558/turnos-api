package apiTurnos.client.presentation.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Respuesta completa con datos de cliente y usuario")
public class ClientResponse {

    // Datos del Client
    @Schema(description = "ID del cliente", example = "1")
    private Long id;

    @Schema(description = "ID del usuario asociado", example = "10")
    private String userId;

    // Datos del UserAccount
    @Schema(description = "Email del cliente", example = "cliente@ejemplo.com")
    private String email;

    @Schema(description = "Nombre completo del cliente", example = "Juan Pérez")
    private String fullName;

    @Schema(description = "Nombre", example = "Juan")
    private String firstName;

    @Schema(description = "Apellido", example = "Pérez")
    private String lastName;

    @Schema(description = "Teléfono", example = "+5491155555555")
    private String phone;

    // Datos específicos del Client
    @Schema(description = "Notas adicionales", example = "Prefiere corte clásico")
    private String notes;

    @Schema(description = "Alergias del cliente",
            example = "[\"Alergia a perfumes\", \"Pelo de gato\"]")
    private Set<String> allergies;

    @Schema(description = "ID del barbero preferido", example = "1")
    private Long preferredBarberId;

    // Estadísticas
    @Schema(description = "Total de turnos atendidos", example = "15")
    private Integer totalAppointments;

    // Preferencias de notificación
    @Schema(description = "Prefiere notificaciones por email", example = "true")
    private Boolean prefersEmailNotifications;

    @Schema(description = "Prefiere notificaciones por SMS", example = "false")
    private Boolean prefersSmsNotifications;

    // Estado
    @Schema(description = "Cliente activo", example = "true")
    private Boolean active;

    // Fechas
    @Schema(description = "Fecha de creación del cliente")
    private LocalDateTime clientSince;

    @Schema(description = "Fecha de última actualización")
    private LocalDateTime updatedAt;

    @Schema(description = "Último login del usuario")
    private LocalDateTime lastLogin;

    @Schema(description = "Estado del usuario")
    private String userStatus;
}