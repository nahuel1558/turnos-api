package apiTurnos.barber.application.command;

import jakarta.persistence.Id;
import lombok.*;
import jakarta.validation.constraints.*;

import java.time.LocalTime;
import java.util.Set;

@Builder
@Data
@NoArgsConstructor @AllArgsConstructor
public class RegisterBarberCommand {

    // User data

    @NotBlank @Id
    private Long idUser;

    // Barber specific data
    @NotBlank
    private String displayName;
    private String professionalTitle;
    private String bio;
    private Set<String> specialties;

    // Schedule
    private LocalTime workStartTime;
    private LocalTime workEndTime;
    private LocalTime breakStartTime;
    private LocalTime breakEndTime;

    @Builder.Default
    private Set<Integer> workDays = Set.of(1, 2, 3, 4, 5); // Lunes-Viernes
}