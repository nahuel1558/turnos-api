package apiTurnos.barber.presentation.dto.request;

import jakarta.validation.constraints.Size;
import lombok.Data;
import java.time.LocalTime;
import java.util.Set;

@Data
public class UpdateBarberRequest {

    @Size(min = 2, max = 100, message = "Display name debe tener entre 2 y 100 caracteres")
    private String displayName;

    @Size(max = 100, message = "Professional title no puede exceder 100 caracteres")
    private String professionalTitle;

    @Size(max = 500, message = "Bio no puede exceder 500 caracteres")
    private String bio;

    private Set<String> specialties;

    private LocalTime workStartTime;
    private LocalTime workEndTime;
    private LocalTime breakStartTime;
    private LocalTime breakEndTime;

    private Set<Integer> workDays;
}