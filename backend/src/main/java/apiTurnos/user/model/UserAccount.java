package apiTurnos.user.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * Entidad de Dominio/Persistencia del usuario.
 *
 * SRP:
 * - Mantiene estado y comportamiento básico del usuario.
 *
 * NO hace:
 * - Guardar en DB (repos)
 * - JWT / security (security layer)
 * - Enviar mails (infra)
 */
@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class UserAccount {

    @Id
    @Column(length = 36)
    private String id; // UUID string (podés cambiarlo a Long si preferís)

    @Column(nullable = false, unique = true, length = 120)
    private String email;

    @Column(length = 30)
    private String phone;

    @Column(nullable = false, length = 80)
    private String firstName;

    @Column(nullable = false, length = 80)
    private String lastName;


    /**
     * Guardar SIEMPRE hash, nunca plain text.
     */
    @Column(nullable = false, length = 255)
    private String passwordHash;

    /**
     *  Relacion con los roles
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_account_roles",
            joinColumns = @JoinColumn(name = "user_account_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    @Builder.Default
    private Set<SystemRole> roles = new HashSet<>();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private UserStatus status;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    private LocalDateTime deletedAt;

    // -------------------------
    // Comportamiento de dominio
    // -------------------------

    public void addRole(SystemRole role) {
        if (role != null && !this.roles.contains(role)) {
            this.roles.add(role);
            markUpdated(null);
        }
    }

    public void removeRole(SystemRole role) {
        if (role != null) {
            this.roles.remove(role);
            markUpdated(null);
        }
    }

    public boolean hasRole(String roleName) {
        return roles.stream().anyMatch(role -> role.getName().equals(roleName));
    }

    public void markUpdated(LocalDateTime now) {
        this.updatedAt = (now == null) ? LocalDateTime.now() : now;
    }

    public void softDelete(LocalDateTime now) {
        this.status = UserStatus.DELETED;
        this.deletedAt = (now == null) ? LocalDateTime.now() : now;
        markUpdated(now);
    }

    public boolean isDeleted() {
        return this.status == UserStatus.DELETED || this.deletedAt != null;
    }

    public String fullName() {
        return (firstName == null ? "" : firstName) + " " + (lastName == null ? "" : lastName);
    }
}
