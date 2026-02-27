package apiTurnos.barber.application.command;

import apiTurnos.barber.domain.model.Barber;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChangeBarberStatusCommand {

    private Long barberId;
    private Barber.ProfessionalStatus newStatus;
}