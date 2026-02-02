package apiTurnos.common.enums;
/**
 * Roles del sistema.
 * Si esto es específico de User, también podría vivir en user/domain.
 * Pero suele reutilizarse para seguridad/autorización.
 */
public enum Role {
    ADMIN,
    BARBER,
    CLIENT
}
