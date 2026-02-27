package apiTurnos.barber.application.command;

import apiTurnos.barber.presentation.dto.request.UpdateBarberRequest;
import lombok.*;

@Data
@Builder
@AllArgsConstructor @NoArgsConstructor
public class UpdateBarberCommand {

    private Long idBarber;
    private UpdateBarberRequest updateBarberRequest;

}
