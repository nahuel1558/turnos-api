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
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true) // Solo lectura para consultas.
@Slf4j
public class ServiceQueryHandler {

    private final ServiceQueryRepository queryRepository;
    private final ServiceMapper serviceMapper;
    private final ServiceSpecificationBuilder specificationBuilder;

    // Listar todos los servicios (con filtros).
    public List<ServiceResponseDTO> handle(GetServicesQuery query) {

        log.info("Procesando query de servicios con filtros: {}", query);

        Specification<ServiceItem> spec = specificationBuilder.buildFromQuery(query);

        // Ordenar por "NOMBRE" de manera ascendente.
        Sort sort = Sort.by(Sort.Direction.ASC, "name");

        List<ServiceItem> serviceItems = queryRepository.findAll(spec, sort);

        log.info("Encontrados {} servicios", serviceItems.size());

        return serviceItems.stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
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

    // Metodo para mapear. Agregar un paquete y clase para estos mapeos aparte.
    private ServiceResponseDTO mapToResponseDTO(ServiceItem serviceItem){
        return serviceMapper.mapToServiceResponse(serviceItem);
    }
}
