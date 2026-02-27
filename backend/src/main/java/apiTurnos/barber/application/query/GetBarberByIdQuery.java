package apiTurnos.barber.application.query;

import lombok.*;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetBarberByIdQuery {

    private Long id;

    public static GetBarberByIdQuery fromId(Long id){
        return GetBarberByIdQuery.builder()
                .id(id)
                .build();
    }
}