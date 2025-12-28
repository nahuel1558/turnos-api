package apiTurnos.service.command;

import lombok.*;

@Data
@Builder
@NoArgsConstructor @AllArgsConstructor
public class DeleteServiceCommand {

    private Long idService;
}
