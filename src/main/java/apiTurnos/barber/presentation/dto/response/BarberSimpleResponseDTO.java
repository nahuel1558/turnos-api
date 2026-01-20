package apiTurnos.barber.presentation.dto.response;

import apiTurnos.barber.domain.model.Barber;
import lombok.*;

import java.time.LocalTime;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BarberSimpleResponseDTO {

    private Long id;
    private String displayName;
    private String professionalTitle;
    private Set<String> specialties;

    // Información resumida del horario
    private LocalTime workStartTime;
    private LocalTime workEndTime;
    private LocalTime breakStartTime;
    private LocalTime breakEndTime;
    private Set<Integer> workDays;

    // Estado
    private Barber.ProfessionalStatus professionalStatus;
    private Boolean active;

    // Metodo para formatear horario
    public String getFormattedWorkSchedule() {
        if (workStartTime == null || workEndTime == null) {
            return "Horario no definido";
        }
        return workStartTime.toString() + " - " + workEndTime.toString();
    }

    // Metodo para obtener días laborales como string
    public String getWorkDaysFormatted() {
        if (workDays == null || workDays.isEmpty()) {
            return "No definido";
        }

        String[] days = {"Dom", "Lun", "Mar", "Mié", "Jue", "Vie", "Sáb"};
        return workDays.stream()
                .sorted()
                .map(day -> days[day])
                .reduce((a, b) -> a + ", " + b)
                .orElse("");
    }
}