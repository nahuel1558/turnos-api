package apiTurnos.client.model;

import apiTurnos.user.model.UserAccount;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Entity
@Table(name = "clients")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // RELACIÓN 1:1 CON USER ACCOUNT
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", unique = true, nullable = false)
    private UserAccount userAccount;

    // PREFERENCIAS
    @Column(name = "preferred_barber_id")
    private Long preferredBarberId;


    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(
            name = "client_allergies",
            joinColumns = @JoinColumn(name = "client_id")
    )
    @Column(name = "allergy", length = 100)
    @Builder.Default
    private Set<String> allergies = new HashSet<>();

    @Column(name = "notes", length = 1000)
    private String notes;

    // FIDELIDAD
    @Column(name = "total_appointments", nullable = false)
    @Builder.Default
    private Integer totalAppointments = 0;

    // NOTIFICACIONES
    @Column(name = "prefers_email_notifications")
    @Builder.Default
    private Boolean prefersEmailNotifications = true;

    @Column(name = "prefers_sms_notifications")
    @Builder.Default
    private Boolean prefersSmsNotifications = false;

    // AUDITORÍA
    @Column(nullable = false)
    @Builder.Default
    private Boolean active = true;

    @Column(name = "client_since", nullable = false, updatable = false)
    @Builder.Default
    private LocalDateTime clientSince = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    // === COMMAND METHODS ===

    public void updatePreferences(String notes) {
        if (notes != null) this.notes = notes;
        this.updatedAt = LocalDateTime.now();
    }

    public void addAllergy(String allergy) {
        if (allergy != null && !allergy.trim().isEmpty()) {
            this.allergies.add(allergy.trim());
            this.updatedAt = LocalDateTime.now();
        }
    }

    public void removeAllergy(String allergy) {
        if (allergy != null) {
            this.allergies.remove(allergy.trim());
            this.updatedAt = LocalDateTime.now();
        }
    }


    public void setNotificationPreferences(Boolean email, Boolean sms) {
        if (email != null) this.prefersEmailNotifications = email;
        if (sms != null) this.prefersSmsNotifications = sms;
        this.updatedAt = LocalDateTime.now();
    }

    // === QUERY METHODS ===

    public String getFullName() {
        return userAccount != null ? userAccount.fullName() : "Cliente";
    }

    public String getEmail() {
        return userAccount != null ? userAccount.getEmail() : null;
    }

    public boolean isActiveClient() {
        return this.active &&
                Optional.ofNullable(userAccount)
                        .map(account -> !account.isDeleted())
                        .orElse(false);
    }

}