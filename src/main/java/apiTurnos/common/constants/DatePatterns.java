package apiTurnos.common.constants;
/**
 * Patrones de fechas/hora usados de manera consistente en toda la app.
 * Evita hardcodear strings en múltiples lugares.
 */
public final class DatePatterns {

    private DatePatterns() {}

    public static final String ISO_DATE = "yyyy-MM-dd";
    public static final String ISO_TIME = "HH:mm";
    public static final String ISO_DATE_TIME = "yyyy-MM-dd'T'HH:mm:ss";
}
