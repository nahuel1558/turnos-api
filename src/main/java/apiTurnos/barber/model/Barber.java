package apiTurnos.barber.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalTime;

@Entity
@Table(name = "barbers")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Barber {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String displayName; // "Juan", "Matías", etc.

    @Column(nullable = false)
    private LocalTime workStart; // 09:00

    @Column(nullable = false)
    private LocalTime workEnd;   // 18:00

    @Column(nullable = false)
    private boolean active;
}

