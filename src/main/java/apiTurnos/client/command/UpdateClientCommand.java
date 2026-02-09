package apiTurnos.client.command;

import apiTurnos.client.presentation.dto.request.UpdateClientRequest;
import lombok.*;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateClientCommand {

    private Long clientId;
    private String notes;
    private Set<String> allergies;
    private Long preferredBarberId;

    private Boolean prefersEmailNotifications;
    private Boolean prefersSmsNotifications;
    private Boolean active;

    public static UpdateClientCommand fromRequest(Long clientId, UpdateClientRequest request) {
        return UpdateClientCommand.builder()
                .clientId(clientId)
                .notes(request.getNotes())
                .allergies(request.getAllergies())
                .preferredBarberId(request.getPreferredBarberId())
                .prefersEmailNotifications(request.getPrefersEmailNotifications())
                .prefersSmsNotifications(request.getPrefersSmsNotifications())
                .active(request.getActive())
                .build();
    }
}
