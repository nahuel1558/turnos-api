package apiTurnos.service.domain.model;

import apiTurnos.service.domain.exception.DomainValidationException;
import apiTurnos.service.presentation.dto.request.ServiceRequestDTO;
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
@NoArgsConstructor (access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
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
    private LocalDateTime updatedAt;

    @PreUpdate
    protected void onUpdate(){
        this.updatedAt = LocalDateTime.now();
    }

    // === FACTORY METHODS (Patrón Factory) ===

    public static ServiceItem create(String name, String description,
                                     Integer durationMinutes, BigDecimal price) {
        validateCreation(name, durationMinutes, price);

        return ServiceItem.builder()
                .name(name.trim())
                .description(description != null ? description.trim() : null)
                .durationMinutes(durationMinutes)
                .price(price)
                .active(true)
                .build();
    }

    private static void validateCreation(String name, Integer durationMinutes, BigDecimal price) {
        if (name == null || name.trim().isEmpty()) {
            throw new DomainValidationException("Service name is required");
        }
        if (durationMinutes == null || durationMinutes <= 0) {
            throw new DomainValidationException("Duration must be positive");
        }
        if (price == null || price.compareTo(BigDecimal.ZERO) < 0) {
            throw new DomainValidationException("Price must be positive or zero");
        }
    }

    // === COMMAND METHODS (Lógica de negocio) ===

    public void update(ServiceRequestDTO dto) {
        validateUpdate(dto);

        if (dto.getName() != null && !dto.getName().equals(this.name)) {
            this.name = dto.getName().trim();
        }
        if (dto.getDescription() != null) {
            this.description = dto.getDescription().trim();
        }
        if (dto.getDurationMinutes() != null) {
            this.durationMinutes = dto.getDurationMinutes();
        }
        if (dto.getPrice() != null) {
            this.price = dto.getPrice();
        }

        this.updatedAt = LocalDateTime.now();
    }

    private void validateUpdate(ServiceRequestDTO dto) {
        if (dto == null) {
            throw new DomainValidationException("Update data cannot be null");
        }
        if (dto.getName() != null && dto.getName().trim().isEmpty()) {
            throw new DomainValidationException("Service name cannot be empty");
        }
        if (dto.getDurationMinutes() != null && dto.getDurationMinutes() <= 0) {
            throw new DomainValidationException("Duration must be positive");
        }
        if (dto.getPrice() != null && dto.getPrice().compareTo(BigDecimal.ZERO) < 0) {
            throw new DomainValidationException("Price must be positive or zero");
        }
    }


    public void deactivate() {
        if (!this.active) {
            throw new DomainValidationException("Service is already deactivated");
        }
        this.active = false;
        this.updatedAt = LocalDateTime.now();
    }

    public void activate() {
        if (this.active) {
            throw new DomainValidationException("Service is already active");
        }
        this.active = true;
        this.updatedAt = LocalDateTime.now();
    }

    // === QUERY METHODS (Solo consulta) ===

    public boolean isActive() {
        return Boolean.TRUE.equals(this.active);
    }

    public boolean hasName(String name) {
        return this.name.equalsIgnoreCase(name.trim());
    }

    public boolean isDurationLessThanOrEqual(Integer minutes) {
        return this.durationMinutes <= minutes;
    }

    public boolean isPriceBetween(BigDecimal min, BigDecimal max) {
        return price.compareTo(min) >= 0 && price.compareTo(max) <= 0;
    }
}
