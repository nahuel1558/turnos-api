package apiTurnos.client.command;

import lombok.Data;
import jakarta.validation.constraints.*;

@Data
public class RegisterClientCommand {
    @NotBlank @Email
    private String email;

    @NotBlank @Size(min = 6)
    private String password;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @Pattern(regexp = "^\\+?[0-9\\s\\-]{10,}$")
    private String phone;

    private Boolean prefersEmailNotifications = true;
    private Boolean prefersSmsNotifications = false;
}