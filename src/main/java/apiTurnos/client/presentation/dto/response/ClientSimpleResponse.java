package apiTurnos.client.presentation.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Respuesta simplificada del cliente (para listados)")
public class ClientSimpleResponse {

    @Schema(description = "ID del cliente", example = "1")
    private Long id;

    @Schema(description = "ID del usuario", example = "10")
    private String userId;

    @Schema(description = "Nombre completo", example = "Juan Pérez")
    private String fullName;

    @Schema(description = "Email", example = "cliente@ejemplo.com")
    private String email;

    @Schema(description = "Teléfono", example = "+5491155555555")
    private String phone;

    @Schema(description = "Total de turnos", example = "15")
    private Integer totalAppointments;

    @Schema(description = "Cliente activo", example = "true")
    private Boolean active;

    @Schema(description = "Fecha de cliente desde")
    private LocalDateTime clientSince;
}