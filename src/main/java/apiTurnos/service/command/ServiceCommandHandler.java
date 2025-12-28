package apiTurnos.service.command;

import apiTurnos.service.dto.ServiceResponseDTO;
import apiTurnos.service.dto.ServiceRequestDTO;
import apiTurnos.service.model.ServiceItem;
import apiTurnos.service.repository.ServiceCommandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ServiceCommandHandler {

    private final ServiceCommandRepository commandRepository;

    // Crear
    public ServiceResponseDTO handleCreate(CreateServiceCommand command){
        ServiceRequestDTO request = command.getServiceRequest();

        // Validar unicidad del nombre.
        if(commandRepository.existByNameAndActiveTrue(request.getName())){
            throw new IllegalArgumentException("Ya existe un servicio activo con ese nombre.");
        }

        // Crear entidad.
        ServiceItem serviceItem = ServiceItem.builder()
                .name(request.getName())
                .description(request.getDescription())
                .durationMinutes(request.getDurationMinutes())
                .price(request.getPrice())
                .active(true)
                .build();

        ServiceItem saved = commandRepository.save(serviceItem);

        return mapToResponseDTO(saved);
    }

    // Actualizar
    public ServiceResponseDTO handleUpdate(UpdateServiceCommand command){
        ServiceRequestDTO requestDTO = command.getServiceRequest();

        // Buscar servicio por ID.
        ServiceItem service = commandRepository.findById(command.getIdService())
                .orElseThrow(() -> new IllegalArgumentException("Servicio no encontrado."));

        // Validar que este activo.
        if (!service.getActive()){
            throw new IllegalArgumentException("No se puede modificar un servicio inactivo");
        }

        // validar unicidad del nombre (excluyendo el actual)
        if(commandRepository.existByNameAndIdNotActiveTrue(requestDTO.getName(), command.getIdService())){
            throw new IllegalArgumentException("Ya existe otro servicio activo con ese nombre.");
        }

        // Actualizar servicio.
        service.updateFromDTO(requestDTO);
        ServiceItem updated =  commandRepository.save(service);

        return mapToResponseDTO(updated);
    }

    // Borrar
    public void handleDelete(DeleteServiceCommand command){
        ServiceItem serviceItem = commandRepository.findById(command.getIdService())
                .orElseThrow(() -> new IllegalArgumentException("Servicio no encontrado"));

        // Soft delete.
        serviceItem.deactive();
        commandRepository.save(serviceItem);
    }

    // Metodo auxiliar para mapear. Debemos crear un paquete de mappers.
    private ServiceResponseDTO mapToResponseDTO(ServiceItem serviceItem){
        return ServiceResponseDTO.builder()
                .idService(serviceItem.getId())
                .name(serviceItem.getName())
                .description(serviceItem.getDescription())
                .durationMinutes(serviceItem.getDurationMinutes())
                .price(serviceItem.getPrice())
                .active(serviceItem.getActive())
                .createdAt(serviceItem.getCreatedAt())
                .updateAt(serviceItem.getUpdateAt())
                .build();
    }
}
