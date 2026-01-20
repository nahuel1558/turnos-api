package apiTurnos.user.model;

import jakarta.persistence.*;
import lombok.*;

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

    @Column(unique = true, nullable = false, length = 50)
    private String name;  // "ROLE_CLIENT", "ROLE_BARBER", "ROLE_ADMIN"

    @Column(length = 200)
    private String description;

    public SystemRole(String name, String description) {
        this.name = name;
        this.description = description;
    }
}