package apiTurnos.barber.domain.exception;

public class BarberDomainException extends RuntimeException {

    public BarberDomainException(String message) {
        super(message);
    }

    public BarberDomainException(String message, Throwable cause) {
        super(message, cause);
    }
}