package apiTurnos.barber.domain.service;

import apiTurnos.barber.domain.model.Barber;
import apiTurnos.barber.domain.exception.BarberDomainException;
import org.springframework.stereotype.Service;

@Service
public class BarberValidator {

    public void validateBarberExists(Barber barber) {
        if (barber == null) {
            throw new BarberDomainException("Barbero no encontrado");
        }
    }

    public void validateBarberIsActive(Barber barber) {
        if (barber != null && !barber.isActive()) {
            throw new BarberDomainException("El barbero está inactivo");
        }
    }

    public void validateDisplayNameUniqueness(String displayName, boolean existsWithSameName) {
        if (existsWithSameName) {
            throw new BarberDomainException("Ya existe un barbero con el nombre: " + displayName);
        }
    }

    public void validateDisplayNameForUpdate(String displayName, Long barberId,
                                             boolean existsWithSameNameAndDifferentId) {
        if (existsWithSameNameAndDifferentId) {
            throw new BarberDomainException("Ya existe otro barbero con el nombre: " + displayName);
        }
    }

    public void validateUserAccountNotAlreadyBarber(String userId, boolean userAlreadyBarber) {
        if (userAlreadyBarber) {
            throw new BarberDomainException("El usuario ya tiene un perfil de barbero");
        }
    }
}