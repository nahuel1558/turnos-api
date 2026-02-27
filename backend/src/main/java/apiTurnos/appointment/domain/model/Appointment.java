package apiTurnos.appointment.domain.model;

import apiTurnos.barber.domain.model.Barber;
import apiTurnos.client.model.Client;
import apiTurnos.service.domain.model.ServiceItem;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Entidad Appointment (Turno).
 *
 * SRP:
 * - Mantiene estado + reglas del turno (cancelación, reprogramación).
 *
 * Importante:
 * - El turno pertenece a un Client (perfil), y el Client está ligado a UserAccount.
 */
@Entity
@Table(name = "appointments")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Cliente que reserva (perfil)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    // Peluquero elegido
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "barber_id", nullable = false)
    private Barber barber;

    // Servicio: corte, barba, etc.
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "service_id", nullable = false)
    private ServiceItem service;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private LocalTime startTime;

    @Column(nullable = false)
    private LocalTime endTime;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AppointmentStatus status;

    /** Factory: crea un turno reservado (default BOOKED). */
    public static Appointment booked(Client client, Barber barber, ServiceItem service,
                                     LocalDate date, LocalTime start, LocalTime end) {
        return Appointment.builder()
                .client(client)
                .barber(barber)
                .service(service)
                .date(date)
                .startTime(start)
                .endTime(end)
                .status(AppointmentStatus.BOOKED)
                .build();
    }

    /** Cancelación idempotente (mantiene histórico). */
    public void cancel() {
        if (this.status == AppointmentStatus.CANCELED) return;
        if (this.status != AppointmentStatus.BOOKED) {
            throw new IllegalStateException("Solo se puede cancelar un turno en estado BOOKED");
        }
        this.status = AppointmentStatus.CANCELED;
    }

    /** Reprogramación: no permite modificar cancelado. */
    public void reschedule(ServiceItem newService, LocalTime newStart, LocalTime newEnd) {
        if (this.status == AppointmentStatus.CANCELED) {
            throw new IllegalStateException("No se puede modificar un turno cancelado");
        }
        this.service = newService;
        this.startTime = newStart;
        this.endTime = newEnd;
    }
}
