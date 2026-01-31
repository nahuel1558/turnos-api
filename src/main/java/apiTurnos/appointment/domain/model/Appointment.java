package apiTurnos.appointment.domain.model;

import apiTurnos.barber.domain.model.Barber;
import apiTurnos.service.domain.model.ServiceItem;
import jakarta.persistence.*;
import lombok.*;
import org.apache.catalina.User;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Entidad Appointment (Turno).
 *
 * Reglas de negocio dentro de la entidad:
 * - Cancelar: sólo si está BOOKED
 * - Update: no permite cambiar a horarios inválidos
 *
 * Esto respeta SOLID:
 * - SRP: Appointment maneja reglas del turno (estado y datos del turno),
 *        no orquesta repositorios ni envía mails (eso va en services/handlers).
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

    // Cliente que reserva
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private User user;

    // Peluquero elegido
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Barber barber;

    // Servicio: corte, barba, etc.
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
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

    /**
     * Factory para crear un turno reservado.
     * Comentado porque en CQRS normalmente el CREATE se hace desde el handler,
     * pero mantener un factory mejora la claridad y encapsula defaults.
     */
    public static Appointment booked(User user, Barber barber, ServiceItem service,
                                     LocalDate date, LocalTime start, LocalTime end) {
        return Appointment.builder()
                .user(user)
                .barber(barber)
                .service(service)
                .date(date)
                .startTime(start)
                .endTime(end)
                .status(AppointmentStatus.BOOKED)
                .build();
    }

    /**
     * Cancela el turno manteniendo histórico.
     */
    public void cancel() {
        if (this.status == AppointmentStatus.CANCELED) {
            return; // idempotente
        }
        this.status = AppointmentStatus.CANCELED;
    }

    /**
     * Actualiza horario y/o servicio, manteniendo consistencia.
     * Si querés permitir cambiar barber, lo agregamos luego (pero ojo con reglas).
     */
    public void reschedule(ServiceItem newService, LocalTime newStart, LocalTime newEnd) {
        if (this.status == AppointmentStatus.CANCELED) {
            throw new IllegalStateException("No se puede modificar un turno cancelado");
        }
        this.service = newService;
        this.startTime = newStart;
        this.endTime = newEnd;
    }
}

