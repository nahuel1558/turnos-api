// GetClientByUserIdQuery.java
package apiTurnos.client.query;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class GetClientByUserIdQuery {
    @NotNull
    private Long userId;
}