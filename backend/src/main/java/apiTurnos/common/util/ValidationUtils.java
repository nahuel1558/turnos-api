package apiTurnos.common.util;
/**
 * Validaciones mínimas reutilizables.
 * Ojo: esto NO reemplaza Bean Validation (@Valid), es complemento.
 */
public final class ValidationUtils {

    private ValidationUtils() {}

    public static void requireNonBlank(String value, String message) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void requireNonNull(Object value, String message) {
        if (value == null) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void requirePositive(int value, String message) {
        if (value <= 0) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void requirePositive(Long value, String message) {
        if (value == null || value <= 0) {
            throw new IllegalArgumentException(message);
        }
    }
}
