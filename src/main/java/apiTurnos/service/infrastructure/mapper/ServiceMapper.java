package apiTurnos.service.infrastructure.mapper;

import apiTurnos.service.presentation.dto.response.ServiceResponseDTO;
import apiTurnos.service.domain.model.ServiceItem;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ServiceMapper {


    // Metodo para mapear de Model a Response.
    public ServiceResponseDTO mapToServiceResponse (ServiceItem serviceItem){

        return ServiceResponseDTO.builder()
                .idService(serviceItem.getId())
                .name(serviceItem.getName())
                .description(serviceItem.getDescription())
                .durationMinutes(serviceItem.getDurationMinutes())
                .price(serviceItem.getPrice())
                .active(serviceItem.getActive())
                .createdAt(serviceItem.getCreatedAt())
                .updateAt(serviceItem.getUpdatedAt())
                .build();
    }

    //Mapear una lista de "ServiceItem" a una lista de "ServiceResponseDTO".
    public List<ServiceResponseDTO> mapToListServiceResponse (List<ServiceItem> serviceItems){
        return serviceItems.stream().map(this::mapToServiceResponse).collect(Collectors.toList());
    }
}
