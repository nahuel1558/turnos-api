package apiTurnos.user.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 120)
    private String email;

    @Column(nullable = false, length = 80)
    private String firstName;

    @Column(nullable = false, length = 80)
    private String lastName;

    @Column(length = 30)
    private String phone;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Role role;

    @Column(nullable = false)
    private boolean active;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    /**
     * Inicializa valores por defecto al crear.
     */
    @PrePersist
    void onCreate() {
        LocalDateTime now = LocalDateTime.now();
        this.createdAt = now;
        this.updatedAt = now;

        //defaults
        if (this.role == null) this.role = Role.CLIENT;
        this.active = true;
    }

    /**
     * Actualiza timestamp en cada update.
     */
    @PreUpdate
    void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    // ---------------------------
    // Métodos de dominio (negocio)
    // ---------------------------

    /**
     * Devuelve el nombre completo "Nombre Apellido".
     */
    public String fullName() {
        return (firstName + "" + lastName).trim();
    }

    /**
     * Marca el usuario como inactivo (baja logica).
     */
    public void desactivate() {
        this.active = false;
    }

    /**
     * Reactiva el usuario.
     */
    public void activate() {
        this.active = true;
    }

    /**
     * Cambia datos basicos del perfil.
     */
    public void updateProfile(String firstName, String lastName, String phone) {
        if (firstName != null && !firstName.isBlank()) this.firstName = firstName.trim();
        if (lastName != null && !lastName.isBlank()) this.lastName = lastName.trim();
        if (phone != null) this.phone = phone.trim();
    }

    /**
     * Cambia el rol (solo deberia hacerlo un ADMIN via command)
     */
    public void changeRole(Role newRole) {
        if (newRole == null) throw new IllegalArgumentException("El rol no puede ser null");
        this.role = newRole;
    }
}