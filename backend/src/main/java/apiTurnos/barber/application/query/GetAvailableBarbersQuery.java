package apiTurnos.barber.application.query;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetAvailableBarbersQuery {

    private LocalDateTime dateTime;
    private String specialty;
}