package apiTurnos.service.query;

import apiTurnos.service.dto.ServiceResponseDTO;
import apiTurnos.service.mapper.ServiceMapper;
import apiTurnos.service.model.ServiceItem;
import apiTurnos.service.repository.ServiceQueryRepository;
import apiTurnos.service.specification.ServiceSpecificationBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true) // Solo lectura para consultas.
@Slf4j
public class ServiceQueryHandler {

    private final ServiceQueryRepository queryRepository;
    private final ServiceMapper serviceMapper;
    private final ServiceSpecificationBuilder specificationBuilder;

    // Listar todos los servicios (con filtros).
    public List<ServiceResponseDTO> handleGetAllServices(GetServicesQuery query) {

        log.info("Procesando query de servicios con filtros: {}", query);

        List<ServiceItem> serviceItems = findAllServiceItemsWithSorting(query);

        log.info("Encontrados {} servicios", serviceItems.size());

        return mapToListServiceResponseDTO(serviceItems);
    }

    //Metodo para comunicarse con "QueryRepository" y traer todos los objetos.
    private List<ServiceItem> findAllServiceItemsWithSorting(GetServicesQuery query){
        Specification<ServiceItem> spec = buildServiceSpecification(query);
        Sort sort = createNameAscSort();
        return queryRepository.findAll(spec, sort);
    }

    //Metodo para contruir el "SERVICE" con las "ESPECIFICACIONES".
    private Specification<ServiceItem> buildServiceSpecification(GetServicesQuery query){
        return specificationBuilder.buildFromQuery(query);
    }

    //Metodo para definir el orden del "SERVICE".
    private Sort createNameAscSort(){
        return Sort.by(Sort.Direction.ASC, "name");
    }

    //Metodo para mapear una "List<ServiceItem>" a "List<ServiceResponseDTO>.
    private List<ServiceResponseDTO> mapToListServiceResponseDTO(List<ServiceItem> serviceItems){
        return serviceMapper.mapToListServiceResponse(serviceItems);
    }

    //Obtener servicio por ID y que este activo.
    public ServiceResponseDTO handleById(GetServiceByIdQuery query){
        return mapToResponseDTO(findServiceItemByIdAndActiveTrue(query));
    }

    // Metodo para encontrar un servicio por ID y que este activo comunicandose con queryRepository.
    private ServiceItem findServiceItemByIdAndActiveTrue(GetServiceByIdQuery query){
        return  queryRepository.findByIdAndActiveTrue(query.getIdService())
                .orElseThrow(() -> new IllegalArgumentException("Servicio no escontrado o inactivo."));
    }

    // Metodo para mapear un objeto de model a response.
    private ServiceResponseDTO mapToResponseDTO(ServiceItem serviceItem){
        return serviceMapper.mapToServiceResponse(serviceItem);
    }
}
