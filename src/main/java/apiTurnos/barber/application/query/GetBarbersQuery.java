package apiTurnos.barber.application.query;

import apiTurnos.barber.domain.model.Barber;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetBarbersQuery {
    private String searchTerm;
    private String specialty;
    private Boolean active;
    private Barber.ProfessionalStatus professionalStatus;
    private Boolean includeInactive;

    public Boolean getActive() {
        if (includeInactive != null && includeInactive) {
            return null; // Incluir todos (activos e inactivos)
        }
        return active != null ? active : true; // Por defecto solo activos
    }
}