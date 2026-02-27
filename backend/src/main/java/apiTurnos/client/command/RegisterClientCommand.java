package apiTurnos.client.command;

import apiTurnos.client.presentation.dto.request.RegisterClientRequest;
import lombok.*;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterClientCommand {

    private String userId; // String por UserAccount.id
    private String notes;
    private Set<String> allergies;
    private Long preferredBarberId;

    private Boolean prefersEmailNotifications;
    private Boolean prefersSmsNotifications;

    public static RegisterClientCommand fromRequest(RegisterClientRequest request) {
        return RegisterClientCommand.builder()
                .userId(request.getUserId())
                .notes(request.getNotes())
                .allergies(request.getAllergies())
                .preferredBarberId(request.getPreferredBarberId())
                .prefersEmailNotifications(request.getPrefersEmailNotifications())
                .prefersSmsNotifications(request.getPrefersSmsNotifications())
                .build();
    }
}
