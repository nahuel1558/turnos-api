package apiTurnos.client.presentation.dto.request;

import lombok.*;

import java.util.Set;

@Data
public class UpdateClientRequest {
    private String notes;
    private Set<String> allergies;
    private Long preferredBarberId;
    private Boolean prefersEmailNotifications;
    private Boolean prefersSmsNotifications;
    private Boolean active;
}
