package apiTurnos.service.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "services")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class ServiceItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name; // "corte", "Barba", "Corte + Barba".

    @Column(nullable = false)
    private Integer durationMinutes; // 30, 45, 60

    @Column(nullable = false)
    private Integer price; // opcional en MVP pero suma
}
