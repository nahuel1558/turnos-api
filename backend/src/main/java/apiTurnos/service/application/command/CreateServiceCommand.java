package apiTurnos.service.application.command;

import apiTurnos.service.presentation.dto.request.ServiceRequestDTO;
import lombok.*;

@Data
@Builder
@NoArgsConstructor @AllArgsConstructor
public class CreateServiceCommand {
    private ServiceRequestDTO serviceRequest;

    public static CreateServiceCommand fromRequest(ServiceRequestDTO requestDTO){
        return CreateServiceCommand.builder()
                .serviceRequest(requestDTO)
                .build();
    }
}
