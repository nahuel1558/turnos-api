package apiTurnos.service.application.command;

import lombok.*;

@Data
@Builder
@NoArgsConstructor @AllArgsConstructor
public class DeleteServiceCommand {

    private Long idService;
}
