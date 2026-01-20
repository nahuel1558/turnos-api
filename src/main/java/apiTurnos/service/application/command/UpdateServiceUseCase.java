package apiTurnos.service.application.command;

import apiTurnos.common.exception.NotFoundException;
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
public class UpdateServiceUseCase {

    private final ServiceCommandRepository repository;
    private final ServiceValidator validator;
    private final ServiceMapper mapper;

    public ServiceResponseDTO execute(UpdateServiceCommand command) {
        // 1. Buscar servicio
        ServiceItem service = findServiceById(command.getIdService());

        // 2. Validar que existe
        validator.validateServiceExists(service);

        // 3. Validar unicidad del nombre (excluyendo el actual)
        validator.validateNameUniquenessForUpdate(
                command.getServiceRequest().getName(),
                command.getIdService(),
                existsServiceByNameAndIdNotActiveTrue(
                        command.getServiceRequest().getName(),
                        command.getIdService()
                )
        );

        // 4. Actualizar (la entidad valida internamente)
        service.update(command.getServiceRequest());

        // 5. Actualizar la entidad ServiceItem.
        ServiceItem updated = saveService(service);

        // 6. Retornar respuesta
        return toResponseDTO(updated);
    }

    /*
     *  ---  METODOS PARA GUARDAR/BUSCAR/MODIFICAR/BORRAR OBJETOS EN REPOSITORY  ---
     */

    private ServiceItem findServiceById(Long idService){
        return repository.findById(idService)
                .orElseThrow(() -> new NotFoundException("ServiceItem no encontrado con ID: " + idService));
    }

    private Boolean existsServiceByNameAndIdNotActiveTrue(String name, Long idService){
        return repository.existByNameAndIdNotActiveTrue(name, idService);
    }

    private ServiceItem saveService(ServiceItem serviceItem){
        return repository.save(serviceItem);
    }


    /*
     *  ---  METODO PARA MAPEAR OBJETO EN MAPPER  ---
     */

    private ServiceResponseDTO toResponseDTO(ServiceItem serviceItem){
        return mapper.mapToServiceResponse(serviceItem);
    }

}