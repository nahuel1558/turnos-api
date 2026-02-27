package apiTurnos.client.presentation.dto.request;

import lombok.*;

import java.util.Set;

@Data
public class RegisterClientRequest {
    private String userId; // String, porque UserAccount.id es String
    private String notes;
    private Set<String> allergies;
    private Long preferredBarberId;
    private Boolean prefersEmailNotifications;
    private Boolean prefersSmsNotifications;
}
