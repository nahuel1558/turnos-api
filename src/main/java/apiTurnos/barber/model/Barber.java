package apiTurnos.barber.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalTime;

/**
 * Representa a un peluquero del sistema.
 *
 * Define su disponibilidad diaria mediante workStart y workEnd.
 * No maneja turnos directamente (eso vive en Appointment).
 */
@Entity
@Table(name = "barbers")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Barber {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Nombre visible para los clientes
    @Column(nullable = false)
    private String displayName; // "Juan", "Matías", etc.

    // Hora de inicio de jornada laboral
    @Column(nullable = false)
    private LocalTime workStart; // 09:00

    // Hora de fin de jornada laboral
    @Column(nullable = false)
    private LocalTime workEnd;   // 18:00

    // Permite desactivar un peluquero sin borrarlo
    @Column(nullable = false)
    private boolean active;
}

