package apiTurnos.service.command;

import apiTurnos.service.dto.response.ServiceResponseDTO;
import apiTurnos.service.dto.request.ServiceRequestDTO;
import apiTurnos.service.mapper.ServiceMapper;
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

    private final ServiceMapper serviceMapper;

    // Crear
    public ServiceResponseDTO handleCreate(CreateServiceCommand command){
        ServiceRequestDTO request = command.getServiceRequest();
        // Validar unicidad del nombre.
        if(existServiceItemByNameAndActiveTrue(request.getName())){
            throw new IllegalArgumentException("Ya existe un servicio activo con ese nombre.");
        }
        // Crear entidad.
        ServiceItem serviceItem = mapToModel(request);

        ServiceItem saved = saveServiceItem(serviceItem);

        return mapToResponseDTO(saved);
    }

    // Actualizar
    public ServiceResponseDTO handleUpdate(UpdateServiceCommand command){
        ServiceRequestDTO requestDTO = command.getServiceRequest();

        // Buscar servicio por ID.
        ServiceItem service = findServiceItemById(command.getIdService());

        // validar unicidad del nombre (excluyendo el actual)
        if(existServiceItemByNameIdNotActiveTrue(requestDTO.getName(), command.getIdService())){
            throw new IllegalArgumentException("Ya existe otro servicio activo con ese nombre.");
        }

        // Actualizar servicio.
        service.updateFromDTO(requestDTO);
        ServiceItem updated =  saveServiceItem(service);

        return mapToResponseDTO(updated);
    }

    // Borrar
    public void handleDelete(DeleteServiceCommand command){
        ServiceItem serviceItem = findServiceItemById(command.getIdService());
        // Soft delete.
        serviceItem.deactive();
        saveServiceItem(serviceItem);
    }

    /*
    *  ---  METODOS PARA GUARDAR/BUSCAR/MODIFICAR/BORRAR OBJETOS EN REPOSITORY  ---
    */

    // Metodo que conecta con el paquete de Repository y guarda el objeto enviado.
    private ServiceItem saveServiceItem(ServiceItem serviceItem){
       return commandRepository.save(serviceItem);
    }

    // Metodo que conecta con el paquete de Repository y busca un servicio por ID.
    private ServiceItem findServiceItemById(Long idService){
        return commandRepository.findById(idService)
                .orElseThrow(() -> new IllegalArgumentException("Servicio no encontrado"));
    }

    // Metodo para saber si existe un Servicio con el mismo nombre que este activado.
    private boolean existServiceItemByNameAndActiveTrue(String name){
        return commandRepository.existByNameAndActiveTrue(name);
    }

    // Metodos para saber si existe un Servicio con el mismo nombre.
    private boolean existServiceItemByNameIdNotActiveTrue(String name, Long idService){
        return commandRepository.existByNameAndIdNotActiveTrue(name, idService);
    }

    /*
    *  ---  METODOS PARA MAPEAR OBJETOS EN MAPPER  ---
    */
    // Metodo que sirve de conexion con la clase de Mapeo de obejtos. De Resquest a Model.
    private ServiceItem mapToModel(ServiceRequestDTO serviceRequestDTO){
        return serviceMapper.mapToServiceItem(serviceRequestDTO);
    }
    // Metodo que sirve de conexion con la clase de Mapeo de obejtos. Este es de Model a Response.
    private ServiceResponseDTO mapToResponseDTO(ServiceItem serviceItem){
        return serviceMapper.mapToServiceResponse(serviceItem);
    }
}
