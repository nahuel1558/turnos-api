package apiTurnos.service.command;

import apiTurnos.service.dto.request.ServiceRequestDTO;
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
