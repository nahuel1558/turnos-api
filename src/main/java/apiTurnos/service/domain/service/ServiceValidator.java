package apiTurnos.service.domain.service;

import apiTurnos.service.domain.model.ServiceItem;
import apiTurnos.service.domain.exception.DomainValidationException;
import org.springframework.stereotype.Service;

@Service
public class ServiceValidator {

    public void validateNameUniqueness(String name, boolean existsWithSameName) {
        if (existsWithSameName) {
            throw new DomainValidationException(
                    "Ya existe un servicio activo con el nombre: " + name
            );
        }
    }

    public void validateNameUniquenessForUpdate(String name, Long serviceId,
                                                boolean existsWithSameNameAndDifferentId) {
        if (existsWithSameNameAndDifferentId) {
            throw new DomainValidationException(
                    "Ya existe otro servicio activo con el nombre: " + name
            );
        }
    }

    public void validateServiceExists(ServiceItem service) {
        if (service == null) {
            throw new DomainValidationException("Servicio no encontrado");
        }
    }

    public void validateServiceIsActive(ServiceItem service) {
        if (service != null && !service.isActive()) {
            throw new DomainValidationException("El servicio está inactivo");
        }
    }
}