package apiTurnos.barber.presentation.dto.response;

import apiTurnos.barber.domain.model.Barber;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BarberResponseDTO {

    private Long id;
    private Long userId;
    private String displayName;
    private String professionalTitle;
    private String bio;
    private Set<String> specialties;

    // Información del usuario
    private String userFullName;
    private String userEmail;
    private String userPhone;

    // Horario
    private LocalTime workStartTime;
    private LocalTime workEndTime;
    private LocalTime breakStartTime;
    private LocalTime breakEndTime;
    private Set<Integer> workDays;

    // Estadísticas
    private Integer totalAppointments;
    private Integer totalReviews;

    // Estado
    private Barber.ProfessionalStatus professionalStatus;
    private Boolean active;

    // Auditoría
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Métodos utilitarios
    public boolean isCurrentlyAvailable() {
        return Boolean.TRUE.equals(active) &&
                professionalStatus == Barber.ProfessionalStatus.AVAILABLE;
    }
}