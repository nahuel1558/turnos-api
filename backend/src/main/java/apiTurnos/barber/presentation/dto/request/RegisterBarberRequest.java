package apiTurnos.barber.presentation.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalTime;
import java.util.Set;

@Data
public class RegisterBarberRequest {

    @NotNull(message = "User ID es requerido")
    private String userId;

    @NotBlank(message = "Display name es requerido")
    @Size(min = 2, max = 100, message = "Display name debe tener entre 2 y 100 caracteres")
    private String displayName;

    @Size(max = 100, message = "Professional title no puede exceder 100 caracteres")
    private String professionalTitle;

    @Size(max = 500, message = "Bio no puede exceder 500 caracteres")
    private String bio;

    private Set<String> specialties;

    @AssertTrue(message = "Work start time debe ser antes de work end time")
    public boolean isWorkTimesValid() {
        if (workStartTime == null || workEndTime == null) return true;
        return workStartTime.isBefore(workEndTime);
    }

    @AssertTrue(message = "Break debe estar dentro del horario laboral")
    public boolean isBreakTimesValid() {
        if (workStartTime == null || workEndTime == null ||
                breakStartTime == null || breakEndTime == null) return true;

        return breakStartTime.isAfter(workStartTime) &&
                breakEndTime.isBefore(workEndTime) &&
                breakStartTime.isBefore(breakEndTime);
    }

    private LocalTime workStartTime;
    private LocalTime workEndTime;
    private LocalTime breakStartTime;
    private LocalTime breakEndTime;

    private Set<@Min(0) @Max(6) Integer> workDays;
}