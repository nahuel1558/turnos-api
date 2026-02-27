package apiTurnos.common.annotations;

import java.lang.annotation.*;

/**
 * Marca una clase como caso de uso de la capa Application (CQRS).
 * No cambia runtime behavior; es "semantic annotation" para legibilidad y arquitectura.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.SOURCE)
@Documented
public @interface UseCase {
    String value() default "";
}
