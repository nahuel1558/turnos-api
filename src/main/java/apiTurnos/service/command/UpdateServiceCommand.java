package apiTurnos.service.command;

import apiTurnos.service.dto.ServiceRequestDTO;
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
