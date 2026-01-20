package apiTurnos.barber.domain.dto;

import apiTurnos.barber.domain.model.Barber;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BarberFiltersDTO {
    private String searchTerm;
    private String specialty;
    private Boolean active;
    private Barber.ProfessionalStatus professionalStatus;

    public static BarberFiltersDTO empty() {
        return BarberFiltersDTO.builder().build();
    }
}