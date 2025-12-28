package apiTurnos.service.query;

import lombok.*;

@Data
@Builder
@NoArgsConstructor @AllArgsConstructor
public class GetServiceByIdQuery {
    private Long idService;
}
