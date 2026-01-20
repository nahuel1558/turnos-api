package apiTurnos.barber.presentation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BarberScheduleResponseDTO {

    private Long barberId;
    private String displayName;

    private LocalTime workStartTime;
    private LocalTime workEndTime;
    private LocalTime breakStartTime;
    private LocalTime breakEndTime;

    private Set<Integer> workDays;
    private String workDaysFormatted;

    // Métodos utilitarios
    public boolean isAvailableAt(LocalTime time) {
        if (workStartTime == null || workEndTime == null) return false;

        boolean withinWorkHours = !time.isBefore(workStartTime) && !time.isAfter(workEndTime);
        boolean withinBreak = breakStartTime != null && breakEndTime != null &&
                !time.isBefore(breakStartTime) && !time.isAfter(breakEndTime);

        return withinWorkHours && !withinBreak;
    }

    public boolean worksOnDay(Integer dayOfWeek) {
        return workDays != null && workDays.contains(dayOfWeek);
    }
}