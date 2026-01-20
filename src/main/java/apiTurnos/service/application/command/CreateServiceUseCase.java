package apiTurnos.service.application.command;

import apiTurnos.service.domain.model.ServiceItem;
import apiTurnos.service.domain.service.ServiceValidator;
import apiTurnos.service.infrastructure.mapper.ServiceMapper;
import apiTurnos.service.infrastructure.repository.ServiceCommandRepository;
import apiTurnos.service.presentation.dto.response.ServiceResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CreateServiceUseCase {

    private final ServiceCommandRepository repository;
    private final ServiceValidator validator;
    private final ServiceMapper mapper;

    public ServiceResponseDTO execute(CreateServiceCommand command) {
        // 1. Validar unicidad del nombre
        validator.validateNameUniqueness(
                command.getServiceRequest().getName(),
                existsServiceByNameAndActiveTrue(command.getServiceRequest().getName())
        );

        // 2. Crear entidad usando Factory Method
        ServiceItem serviceItem = ServiceItem.create(
                command.getServiceRequest().getName(),
                command.getServiceRequest().getDescription(),
                command.getServiceRequest().getDurationMinutes(),
                command.getServiceRequest().getPrice()
        );

        // 3. Persistir en memoria y guardar en la base de datos.
        ServiceItem saved = saveService(serviceItem);

        // 4. Retornar respuesta mapeada a Response.
        return toResponseDTO(saved);
    }

    /*
     *  ---  METODOS PARA GUARDAR/BUSCAR/MODIFICAR/BORRAR OBJETOS EN REPOSITORY  ---
     */

    private boolean existsServiceByNameAndActiveTrue(String name){
        return  repository.existByNameAndActiveTrue(name);
    }

    private ServiceItem saveService(ServiceItem serviceItem){
        return  repository.save(serviceItem);
    }

    /*
     *  ---  METODO PARA MAPEAR OBJETO EN MAPPER  ---
     */

    private ServiceResponseDTO toResponseDTO(ServiceItem serviceItem){
        return mapper.mapToServiceResponse(serviceItem);
    }
}