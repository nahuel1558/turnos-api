package apiTurnos.barber.domain.model;

import apiTurnos.barber.domain.exception.BarberDomainException;
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
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Barber {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", unique = true, nullable = false)
    private UserAccount userAccount;

    @Column(name = "display_name", nullable = false, length = 100)
    private String displayName;

    @Column(name = "professional_title", length = 100)
    private String professionalTitle;

    @Column(length = 500)
    private String bio;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(
            name = "barber_specialties",
            joinColumns = @JoinColumn(name = "barber_id")
    )
    @Column(name = "specialty", length = 100)
    @Builder.Default
    private Set<String> specialties = new HashSet<>();

    @Column(name = "work_start_time")
    private LocalTime workStartTime;

    @Column(name = "work_end_time")
    private LocalTime workEndTime;

    @Column(name = "break_start_time")
    private LocalTime breakStartTime;

    @Column(name = "break_end_time")
    private LocalTime breakEndTime;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(
            name = "barber_work_days",
            joinColumns = @JoinColumn(name = "barber_id")
    )
    @Column(name = "work_day")
    @Builder.Default
    private Set<Integer> workDays = Set.of(1, 2, 3, 4, 5);

    @Column(name = "total_appointments", nullable = false)
    @Builder.Default
    private Integer totalAppointments = 0;

    @Column(name = "total_reviews", nullable = false)
    @Builder.Default
    private Integer totalReviews = 0;

    public enum ProfessionalStatus {
        AVAILABLE, BUSY, ON_BREAK, ON_VACATION, SICK_LEAVE, UNAVAILABLE
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "professional_status", nullable = false, length = 20)
    @Builder.Default
    private ProfessionalStatus professionalStatus = ProfessionalStatus.AVAILABLE;

    @Builder.Default
    @Column(nullable = false)
    private Boolean active = true;

    @Builder.Default
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    // === FACTORY METHODS ===

    public static Barber create(UserAccount userAccount, String displayName,
                                String professionalTitle, String bio) {
        validateCreation(userAccount, displayName);

        return Barber.builder()
                .userAccount(userAccount)
                .displayName(displayName.trim())
                .professionalTitle(professionalTitle != null ? professionalTitle.trim() : null)
                .bio(bio != null ? bio.trim() : null)
                .active(true)
                .build();
    }

    private static void validateCreation(UserAccount userAccount, String displayName) {
        if (userAccount == null) {
            throw new BarberDomainException("User account is required");
        }
        if (displayName == null || displayName.trim().isEmpty()) {
            throw new BarberDomainException("Display name is required");
        }
    }

    // === COMMAND METHODS (Business Logic) ===

    public void updateInformation(String displayName, String professionalTitle, String bio) {
        if (displayName != null) {
            if (displayName.trim().isEmpty()) {
                throw new BarberDomainException("Display name cannot be empty");
            }
            this.displayName = displayName.trim();
        }

        if (professionalTitle != null) {
            this.professionalTitle = professionalTitle.trim();
        }

        if (bio != null) {
            this.bio = bio.trim();
        }

        this.updatedAt = LocalDateTime.now();
    }

    public void addSpecialty(String specialty) {
        if (specialty == null || specialty.trim().isEmpty()) {
            throw new BarberDomainException("Specialty cannot be empty");
        }
        this.specialties.add(specialty.trim());
        this.updatedAt = LocalDateTime.now();
    }

    public void removeSpecialty(String specialty) {
        if (specialty != null) {
            this.specialties.remove(specialty.trim());
            this.updatedAt = LocalDateTime.now();
        }
    }

    public void setSchedule(LocalTime workStartTime, LocalTime workEndTime,
                            LocalTime breakStartTime, LocalTime breakEndTime) {
        validateSchedule(workStartTime, workEndTime, breakStartTime, breakEndTime);

        this.workStartTime = workStartTime;
        this.workEndTime = workEndTime;
        this.breakStartTime = breakStartTime;
        this.breakEndTime = breakEndTime;
        this.updatedAt = LocalDateTime.now();
    }

    private void validateSchedule(LocalTime workStartTime, LocalTime workEndTime,
                                  LocalTime breakStartTime, LocalTime breakEndTime) {
        if (workStartTime != null && workEndTime != null) {
            if (!workStartTime.isBefore(workEndTime)) {
                throw new BarberDomainException("Work start time must be before end time");
            }

            if (breakStartTime != null && breakEndTime != null) {
                if (!breakStartTime.isBefore(breakEndTime)) {
                    throw new BarberDomainException("Break start time must be before end time");
                }
                if (!breakStartTime.isAfter(workStartTime) || !breakEndTime.isBefore(workEndTime)) {
                    throw new BarberDomainException("Break must be within work hours");
                }
            }
        }
    }

    public void setWorkDays(Set<Integer> workDays) {
        if (workDays == null || workDays.isEmpty()) {
            throw new BarberDomainException("At least one work day is required");
        }
        for (Integer day : workDays) {
            if (day < 0 || day > 6) {
                throw new BarberDomainException("Work day must be between 0 (Sunday) and 6 (Saturday)");
            }
        }
        this.workDays = Set.copyOf(workDays);
        this.updatedAt = LocalDateTime.now();
    }

    public void changeProfessionalStatus(ProfessionalStatus newStatus) {
        if (newStatus == null) {
            throw new BarberDomainException("Professional status cannot be null");
        }
        this.professionalStatus = newStatus;
        this.updatedAt = LocalDateTime.now();
    }

    public void deactivate() {
        if (!this.active) {
            throw new BarberDomainException("Barber is already deactivated");
        }
        this.active = false;
        this.updatedAt = LocalDateTime.now();
    }

    public void activate() {
        if (this.active) {
            throw new BarberDomainException("Barber is already active");
        }
        this.active = true;
        this.updatedAt = LocalDateTime.now();
    }

    public void incrementAppointments() {
        this.totalAppointments++;
        this.updatedAt = LocalDateTime.now();
    }

    public void addReview() {
        this.totalReviews++;
        this.updatedAt = LocalDateTime.now();
    }

    // === QUERY METHODS ===

    public boolean isActive() {
        return Boolean.TRUE.equals(this.active);
    }

    public boolean isCurrentlyAvailable() {
        return this.active && this.professionalStatus == ProfessionalStatus.AVAILABLE;
    }

    public boolean isAvailableOnDay(Integer dayOfWeek) {
        return this.workDays.contains(dayOfWeek) &&
                this.active &&
                this.professionalStatus != ProfessionalStatus.ON_VACATION &&
                this.professionalStatus != ProfessionalStatus.SICK_LEAVE;
    }

    public boolean isWorkingAt(LocalTime time) {
        if (workStartTime == null || workEndTime == null) return false;

        boolean isInWorkHours = !time.isBefore(workStartTime) && !time.isAfter(workEndTime);
        boolean isInBreak = breakStartTime != null && breakEndTime != null &&
                !time.isBefore(breakStartTime) && !time.isAfter(breakEndTime);

        return isInWorkHours && !isInBreak;
    }

    public boolean hasSpecialty(String specialty) {
        return specialties.contains(specialty);
    }

    public String getUserFullName() {
        return userAccount != null ? userAccount.getFullName() : displayName;
    }

    public String getUserEmail() {
        return userAccount != null ? userAccount.getEmail() : null;
    }

    public String getUserPhone() {
        return userAccount != null ? userAccount.getPhone() : null;
    }
}