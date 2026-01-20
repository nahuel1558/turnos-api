package apiTurnos.user.command;


import lombok.*;

@Data
@Builder
@NoArgsConstructor @AllArgsConstructor
public class RegisterUserCommand {
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String phone;
}
