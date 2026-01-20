package apiTurnos.client.command;

import lombok.Data;

@Data
public class UpdateClientCommand {
    private String notes;
    private Boolean prefersEmailNotifications;
    private Boolean prefersSmsNotifications;
}