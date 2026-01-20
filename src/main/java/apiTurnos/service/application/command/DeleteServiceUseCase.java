package apiTurnos.service.application.command;

import apiTurnos.common.exception.NotFoundException;
import apiTurnos.service.domain.model.ServiceItem;
import apiTurnos.service.domain.service.ServiceValidator;
import apiTurnos.service.infrastructure.repository.ServiceCommandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class DeleteServiceUseCase {

    private final ServiceCommandRepository repository;
    private final ServiceValidator validator;

    public void execute(DeleteServiceCommand command) {
        // 1. Buscar servicio por id.
        ServiceItem service = findService(command.getIdService());

        // 2. Validar que existe.
        validator.validateServiceExists(service);

        // 3. Eliminar/Desactivar (soft delete).
        service.deactivate();

        // 4. Persistir en la base de datos.
        saveService(service);
    }

    /*
     *  ---  METODOS PARA GUARDAR/BUSCAR/MODIFICAR/BORRAR OBJETOS EN REPOSITORY  ---
     */

    private ServiceItem findService(Long idService){
        return repository.findById(idService)
                .orElseThrow(() -> new NotFoundException("Servicio no encontrado"));
    }

    private void saveService (ServiceItem serviceItem){
        repository.save(serviceItem);
    }
}