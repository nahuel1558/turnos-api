package apiTurnos.barber.application.command;


import lombok.*;

@Data
@Builder
@NoArgsConstructor @AllArgsConstructor
public class DeleteBarberCommand {

    private Long idBarber;
}
