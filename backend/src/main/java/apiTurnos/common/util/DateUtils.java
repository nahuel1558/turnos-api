package apiTurnos.common.util;

import apiTurnos.common.constants.DatePatterns;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Utilidades de fechas/horas.
 *
 * Buenas prácticas:
 * - Los utils deben ser deterministas y sin dependencias de Spring.
 * - Si algo requiere config (timezone), se pasa por parámetro.
 */
public final  class DateUtils {

    private DateUtils() {}

    private static final DateTimeFormatter ISO_DATE = DateTimeFormatter.ofPattern(DatePatterns.ISO_DATE);
    private static final DateTimeFormatter ISO_TIME = DateTimeFormatter.ofPattern(DatePatterns.ISO_TIME);

    /**
     * Parsea "yyyy-MM-dd" a LocalDate.
     * @throws IllegalArgumentException si el texto no es válido
     */
    public static LocalDate parseIsoDate(String text) {
        try {
            return LocalDate.parse(text, ISO_DATE);
        } catch (DateTimeParseException ex) {
            throw new IllegalArgumentException("Fecha inválida (formato esperado: " + DatePatterns.ISO_DATE + ")");
        }
    }

    /**
     * Parsea "HH:mm" a LocalTime.
     * @throws IllegalArgumentException si el texto no es válido
     */
    public static LocalTime parseIsoTime(String text) {
        try {
            return LocalTime.parse(text, ISO_TIME);
        } catch (DateTimeParseException ex) {
            throw new IllegalArgumentException("Hora inválida (formato esperado: " + DatePatterns.ISO_TIME + ")");
        }
    }

    /**
     * Combina LocalDate + LocalTime en LocalDateTime.
     * Evita repetir "LocalDateTime.of(...)" por toda la app.
     */
    public static LocalDateTime at(LocalDate date, LocalTime time) {
        return LocalDateTime.of(date, time);
    }

    /**
     * Retorna true si (candidateStart, candidateEnd) está dentro del rango [workStart, workEnd].
     * Importante para validaciones de agenda.
     */
    public static boolean isWithin(LocalTime candidateStart, LocalTime candidateEnd, LocalTime workStart, LocalTime workEnd) {
        return !candidateStart.isBefore(workStart) && !candidateEnd.isAfter(workEnd);
    }

    /**
     * Suma minutos con seguridad.
     */
    public static LocalTime plusMinutes(LocalTime time, int minutes) {
        return time.plusMinutes(minutes);
    }
}
