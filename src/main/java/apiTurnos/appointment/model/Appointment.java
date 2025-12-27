package apiTurnos.appointment.model;

import apiTurnos.barber.model.Barber;
import apiTurnos.service.model.ServiceItem;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Representa un turno reservado por un cliente.
 *
 * Es la entidad central del dominio de turnos.
 */
@Entity
@Table(
        name = "appointments",
        indexes = {
                @Index(name = "idx_appointment_barber_date", columnList = "barber_id, date")
        }
)
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Peluquero que realiza el servicio
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Barber barber;

    // Servicio asociado al turno
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private ServiceItem service;

    // Fecha del turno
    @Column(nullable = false)
    private LocalDate date;

    // Hora de inicio del turno
    @Column(nullable = false)
    private LocalTime startTime;

    // Hora de fin del turno (startTime + duración del servicio)
    @Column(nullable = false)
    private LocalTime endTime;

    // Estado del turno (reservado / cancelado)
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AppointmentStatus status;
}

