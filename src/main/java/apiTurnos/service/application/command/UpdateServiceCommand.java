package apiTurnos.service.application.command;

import apiTurnos.service.presentation.dto.request.ServiceRequestDTO;
import lombok.*;

@Data
@Builder
@AllArgsConstructor @NoArgsConstructor
public class UpdateServiceCommand {

    private Long idService;
    private ServiceRequestDTO serviceRequest;

    public static UpdateServiceCommand fromRequest(Long id, ServiceRequestDTO requestDTO){
        return UpdateServiceCommand.builder()
                .idService(id)
                .serviceRequest(requestDTO)
                .build();
    }
}
