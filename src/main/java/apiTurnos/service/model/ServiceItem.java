package apiTurnos.service.model;

import jakarta.persistence.*;
import lombok.*;

/**
 * Representa un servicio ofrecido por la peluquería.
 * Ejemplos: Corte, Barba, Corte + Barba.
 *
 * Es utilizado para calcular:
 * - duración del turno
 * - precio del servicio
 */
@Entity
@Table(name = "services")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class ServiceItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Nombre visible del servicio
    @Column(nullable = false)
    private String name; // "corte", "Barba", "Corte + Barba".

    // Duración del servicio en minutos (30, 45, 60, etc.)
    @Column(nullable = false)
    private Integer durationMinutes; // 30, 45, 60

    // Precio del servicio (opcional en MVP, pero útil comercialmente)
    @Column(nullable = false)
    private Integer price; // opcional en MVP pero suma
}
