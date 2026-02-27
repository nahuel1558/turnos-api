package apiTurnos.user.model;

import jakarta.persistence.*;
import lombok.*;

/**
 * Entidad para roles del sistema (si querés RBAC más adelante).
 * Por ahora es opcional, pero ya queda preparada.
 */
@Entity
@Table(name = "system_roles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SystemRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, unique = true, length = 50)
    private String name;  // "ROLE_ADMIN", "ROLE_BARBER", "ROLE_CLIENT"

    public SystemRole(String name) {
        this.name = name;
    }
}
