package apiTurnos.service.dto.request;
/**
 * Clase utilizada para la creacion/actualizacion del Service.
 * */


import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ServiceRequestDTO {

    // Anotacion que no permite que este en blanco la variable.
    @NotBlank(message = "El nombre es obligatorio")
    //Limita el tamaño de la variable.
    @Size(min = 2, max = 100, message = "El nombre de tener entre 2 y 100 caracteres")
    private String name;

    //Limita el tamaño de la variable.
    @Size(max = 500, message = "La duracion es obligatoria")
    private String description;

    // No permite que este en blanco esta variable.
    @NotNull(message = "La duracion es obligatoria")

    //Valor minimo que permite tener esta variable.
    @Min(value = 1, message = "La duracion minima es 1 minuto")

    //Valor máximo que permite tener esta variable.
    @Max(value = 480, message = "La duracion maxima es 480 minutos (8 horas)")
    private Integer durationMinutes;

    //No permite que este en blanco esta variable.
    @NotNull(message = "El precio es obligatorio")

    //Cantidad minima que permite tener esta variable.
    @DecimalMin(value = "0.0", inclusive = false, message = "El precio debe ser mayor a 0")

    //Cantidad de digitos máximos que debe tener esta variable.
    @Digits(integer = 6, fraction = 2, message = "Precio inválido. Máximo 999999.99")
    private BigDecimal price;
}
