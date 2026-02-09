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

    /**
     * Relación 1:1 con la cuenta global del usuario.
     * - El userId es String (UUID-like) según tu UserAccount.
     */
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", unique = true, nullable = false)
    private UserAccount userAccount;

    // Preferencias
    @Column(name = "preferred_barber_id")
    private Long preferredBarberId;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "client_allergies", joinColumns = @JoinColumn(name = "client_id"))
    @Column(name = "allergy", length = 100)
    @Builder.Default
    private Set<String> allergies = new HashSet<>();

    @Column(name = "notes", length = 1000)
    private String notes;

    // Fidelidad
    @Column(name = "total_appointments", nullable = false)
    @Builder.Default
    private Integer totalAppointments = 0;

    // Notificaciones
    @Column(name = "prefers_email_notifications", nullable = false)
    @Builder.Default
    private Boolean prefersEmailNotifications = true;

    @Column(name = "prefers_sms_notifications", nullable = false)
    @Builder.Default
    private Boolean prefersSmsNotifications = false;

    // Auditoría
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

    // ====== Domain behavior (simple y seguro para MVP) ======

    public void updateProfile(String notes, Set<String> allergies, Long preferredBarberId) {
        if (notes != null) this.notes = notes;
        if (allergies != null) this.allergies = new HashSet<>(allergies);
        if (preferredBarberId != null) this.preferredBarberId = preferredBarberId;
        this.updatedAt = LocalDateTime.now();
    }

    public void setNotificationPreferences(Boolean email, Boolean sms) {
        if (email != null) this.prefersEmailNotifications = email;
        if (sms != null) this.prefersSmsNotifications = sms;
        this.updatedAt = LocalDateTime.now();
    }

    // ====== Helpers de lectura ======

    public String getFullName() {
        return userAccount != null ? userAccount.fullName() : "Cliente";
    }

    public String getEmail() {
        return userAccount != null ? userAccount.getEmail() : null;
    }

    public boolean isActiveClient() {
        return Boolean.TRUE.equals(this.active) &&
                Optional.ofNullable(userAccount).map(a -> !a.isDeleted()).orElse(false);
    }
}
