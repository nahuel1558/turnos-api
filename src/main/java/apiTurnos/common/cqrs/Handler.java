package apiTurnos.common.cqrs;

/**
 * Contrato genérico para handlers CQRS.
 *
 * Principios SOLID:
 * - SRP: cada handler hace una cosa (un caso de uso).
 * - DIP: el handler depende de abstracciones (repositorios/ports).
 */
@FunctionalInterface
public interface Handler<I, O> {

    /**
     * Ejecuta la operación del caso de uso.
     */
    O handle(I input);
}
