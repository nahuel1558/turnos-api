package apiTurnos.barber.presentation.dto.request;

import apiTurnos.barber.domain.model.Barber;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ChangeBarberStatusRequest {

    @NotNull(message = "Professional status es requerido")
    private Barber.ProfessionalStatus newStatus;
}