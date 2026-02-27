package apiTurnos.common.cqrs;
/**
 * Marker interface para Queries (CQRS).
 * Una Query representa una operación de lectura.
 *
 * Ej: GetAvailableSlotsQuery, GetUserByIdQuery
 */
public interface Query<R> {
    // Marker, sin métodos.
}
