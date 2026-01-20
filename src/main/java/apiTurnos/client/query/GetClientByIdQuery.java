package apiTurnos.client.query;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class GetClientByIdQuery {
    @NotNull
    private Long clientId;
}