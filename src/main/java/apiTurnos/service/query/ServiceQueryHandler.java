package apiTurnos.service.query;

import apiTurnos.service.dto.ServiceResponseDTO;
import apiTurnos.service.mapper.ServiceMapper;
import apiTurnos.service.model.ServiceItem;
import apiTurnos.service.repository.ServiceQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true) // Solo lectura para consultas.
public class ServiceQueryHandler {

    private final ServiceQueryRepository queryRepository;
    private final ServiceMapper serviceMapper;

    // Listar todos los servicios (con filtros). Aca hay que modificar y separar los metodos.
    public List<ServiceResponseDTO> handle(GetServicesQuery query){
        List<ServiceItem> serviceItems;

        if (query.getIncludeInactive() != null && query.getIncludeInactive()){
            //Incluye los inactivos.
            serviceItems = queryRepository.findAll();
        } else if (query.getSearchTerm() != null &&  !query.getSearchTerm().isEmpty()) {
            // Busca por termino.
            serviceItems = queryRepository.searchActiveServices(query.getSearchTerm());
        } else if (query.getMaxDuration() != null) {
            // Filtrar por duración máxima
            serviceItems = queryRepository.findByActiveTrueAndDurationMinutesLessThanEqual(query.getMaxDuration());
        } else if (query.getMinPrice() != null && query.getMaxPrice() != null) {
            // Filtrar por rango de precio
            serviceItems = queryRepository.findByActiveTrueAndPriceBetween(query.getMinPrice(), query.getMaxPrice());
        } else {
            // Solo activos
            serviceItems = queryRepository.findAllByActiveTrue();
        }

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
