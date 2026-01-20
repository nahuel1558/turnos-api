package apiTurnos.barber.model;

import apiTurnos.user.model.UserAccount;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalTime;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "barbers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"specialties", "workDays"})
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Barber {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    // RELACIÓN CON USER ACCOUNT (para autenticación)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", unique = true, nullable = false)
    private UserAccount userAccount;

    // DATOS PÚBLICOS/PROFESIONALES
    @Column(name = "display_name", nullable = false, length = 100)
    private String displayName;  // Ej: "Carlos Estilista", "Pepe el Barbero"

    @Column(name = "professional_title", length = 100)
    private String professionalTitle;  // Ej: "Estilista Senior", "Especialista en Barba"

    @Column(length = 500)
    private String bio;  // Descripción

    // ESPECIALIDADES
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(
            name = "barber_specialties",
            joinColumns = @JoinColumn(name = "barber_id")
    )
    @Column(name = "specialty", length = 100)
    @Builder.Default
    private Set<String> specialties = new HashSet<>();

    // HORARIO LABORAL
    @Column(name = "work_start_time")
    private LocalTime workStartTime;  // Ej: 09:00

    @Column(name = "work_end_time")
    private LocalTime workEndTime;    // Ej: 18:00

    @Column(name = "break_start_time")
    private LocalTime breakStartTime; // Ej: 13:00

    @Column(name = "break_end_time")
    private LocalTime breakEndTime;   // Ej: 14:00

    // DÍAS DE TRABAJO (0=Domingo, 1=Lunes, ..., 6=Sábado)
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(
            name = "barber_work_days",
            joinColumns = @JoinColumn(name = "barber_id")
    )
    @Column(name = "work_day")
    @Builder.Default
    private Set<Integer> workDays = Set.of(1, 2, 3, 4, 5);  // Lunes-Viernes por defecto

    // ESTADÍSTICAS
    @Column(name = "total_appointments", nullable = false)
    @Builder.Default
    private Integer totalAppointments = 0;

    @Column(name = "total_reviews", nullable = false)
    @Builder.Default
    private Integer totalReviews = 0;

    // ESTADO
    public enum ProfessionalStatus {
        AVAILABLE,        // Disponible para trabajar
        BUSY,             // Ocupado (en una cita)
        ON_BREAK,         // En descanso
        ON_VACATION,      // De vacaciones
        SICK_LEAVE,       // Licencia médica
        UNAVAILABLE       // No disponible
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "professional_status", nullable = false, length = 20)
    @Builder.Default
    private ProfessionalStatus professionalStatus = ProfessionalStatus.AVAILABLE;

    // ESTADO DEL REGISTRO
    @Column(nullable = false)
    @Builder.Default
    private Boolean active = true;

    // AUDITORÍA
    @Column(name = "created_at", nullable = false, updatable = false)
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    // === MÉTODOS DE NEGOCIO ===

    /**
     * Obtiene el nombre completo del usuario asociado
     */
    public String getUserFullName() {
        return userAccount != null ?
                userAccount.getFirstName() + " " + userAccount.getLastName() :
                displayName;
    }

    /**
     * Obtiene el email del usuario asociado
     */
    public String getUserEmail() {
        return userAccount != null ? userAccount.getEmail() : null;
    }

    /**
     * Obtiene el teléfono del usuario asociado
     */
    public String getUserPhone() {
        return userAccount != null ? userAccount.getPhone() : null;
    }

    /**
     * Verifica si el barbero está disponible para trabajar en un día específico
     */
    public boolean isAvailableOnDay(Integer dayOfWeek) {
        return this.workDays.contains(dayOfWeek) &&
                this.active &&
                this.professionalStatus != ProfessionalStatus.ON_VACATION &&
                this.professionalStatus != ProfessionalStatus.SICK_LEAVE;
    }

    /**
     * Verifica si el barbero está disponible en este momento
     */
    public boolean isCurrentlyAvailable() {
        return this.active &&
                this.professionalStatus == ProfessionalStatus.AVAILABLE;
    }

    /**
     * Agrega una especialidad
     */
    public void addSpecialty(String specialty) {
        if (specialty != null && !specialty.trim().isEmpty()) {
            this.specialties.add(specialty.trim());
        }
    }

    /**
     * Incrementa el contador de citas atendidas
     */
    public void incrementAppointments() {
        this.totalAppointments++;
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * Cambia el estado profesional
     */
    public void changeProfessionalStatus(ProfessionalStatus newStatus) {
        this.professionalStatus = newStatus;
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * Activa/desactiva el barbero
     */
    public void setActive(boolean active) {
        this.active = active;
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * Verifica si el barbero trabaja en un horario específico
     */
    public boolean isWorkingAt(LocalTime time) {
        if (workStartTime == null || workEndTime == null) return false;

        // Considera el break
        boolean isInWorkHours = !time.isBefore(workStartTime) && !time.isAfter(workEndTime);
        boolean isInBreak = breakStartTime != null && breakEndTime != null &&
                !time.isBefore(breakStartTime) && !time.isAfter(breakEndTime);

        return isInWorkHours && !isInBreak;
    }
}