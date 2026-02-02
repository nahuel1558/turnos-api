package apiTurnos.common.constants;

/**
 * Codigos de error estandarizados para toda la API.
 * Util para que el front/mobile o integraciones automaticen manejo de errores.
 *
 * Recomendación:
 * - Mantener nombres estables.
 * - No usar mansajes como codigo.
 */
public final class ErrorCodes {

    private ErrorCodes() {
        // Evita instanciación.
    }

    public static final String VALIDATION_ERROR = "VALIDATION_ERROR";
    public static final String NOT_FOUND = "NOT_FOUND";
    public static final String CONFLICT = "CONFLICT";
    public static final String UNAUTHORIZED = "UNAUTHORIZED";
    public static final String FORBIDDEN = "FORBIDDEN";
    public static final String INTERNAL_ERROR = "INTERNAL_ERROR";
}
