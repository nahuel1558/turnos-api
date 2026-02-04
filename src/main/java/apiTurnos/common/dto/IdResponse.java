package apiTurnos.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * DTO genérico para respuestas que solo devuelven un identificador.
 *
 * ✔ Se usa típicamente en comandos CREATE / DELETE
 * ✔ Evita devolver entidades completas
 * ✔ Reutilizable en cualquier módulo (user, appointment, barber, etc.)
 *
 * Ejemplo de uso:
 * - Crear usuario → devuelve ID
 * - Crear turno → devuelve ID
 * - Eliminar entidad → devuelve ID eliminado
 */
@Getter
@AllArgsConstructor
public class IdResponse {

    /**
     * Identificador de la entidad afectada.
     */
    private final String id;
}
