package apiTurnos.service.model;

import apiTurnos.service.dto.request.ServiceRequestDTO;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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
    @Column(nullable = false, length = 100)
    private String name; // "corte", "Barba", "Corte + Barba".

    // Descripcion del tipo de servicio, no es obligatorio poner una descripcion.
    @Column(length = 500)
    private String description;

    // Duración del servicio en minutos (30, 45, 60, etc.)
    @Column(nullable = false)
    private Integer durationMinutes; // 30, 45, 60

    // Precio del servicio - "precision" Cantidad de dígitos totales. -/- "scale" Cantidad de decimales.
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Builder.Default
    @Column(nullable = false)
    private Boolean active = true;

    @Builder.Default
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column
    private LocalDateTime updateAt;

    @PreUpdate
    protected void onUpdate(){
        this.updateAt = LocalDateTime.now();
    }

    // Metodo para desactivar le service (Soft Delete)
    public void deactive(){
        this.active = false;
        this.updateAt = LocalDateTime.now();
    }

    // Metodo para actualizar desde el DTO.
    public void updateFromDTO(ServiceRequestDTO dto){
        this.name = dto.getName();
        this.description = dto.getDescription();
        this.durationMinutes = dto.getDurationMinutes();
        this.price = dto.getPrice();
        this.updateAt = LocalDateTime.now();
    }
}
