package apiTurnos.service.mapper;

import apiTurnos.service.dto.request.ServiceRequestDTO;
import apiTurnos.service.dto.response.ServiceResponseDTO;
import apiTurnos.service.model.ServiceItem;

import java.util.List;
import java.util.stream.Collectors;


public class ServiceMapper {

    // Metodo para mapear de Request a Model.
    public ServiceItem mapToServiceItem (ServiceRequestDTO serviceRequestDTO){

        ServiceItem serviceItem = ServiceItem.builder()
                .name(serviceRequestDTO.getName())
                .description(serviceRequestDTO.getDescription())
                .durationMinutes(serviceRequestDTO.getDurationMinutes())
                .price(serviceRequestDTO.getPrice())
                .active(true)
                .build();

        return serviceItem;
    }

    // Metodo para mapear de Model a Response.
    public ServiceResponseDTO mapToServiceResponse (ServiceItem serviceItem){

        ServiceResponseDTO serviceResponseDTO = ServiceResponseDTO.builder()
                .idService(serviceItem.getId())
                .name(serviceItem.getName())
                .description(serviceItem.getDescription())
                .durationMinutes(serviceItem.getDurationMinutes())
                .price(serviceItem.getPrice())
                .active(serviceItem.getActive())
                .createdAt(serviceItem.getCreatedAt())
                .updateAt(serviceItem.getUpdateAt())
                .build();

        return  serviceResponseDTO;
    }

    //Mapear una lista de "ServiceItem" a una lista de "ServiceResponseDTO".
    public List<ServiceResponseDTO> mapToListServiceResponse (List<ServiceItem> serviceItems){
        return serviceItems.stream().map(this::mapToServiceResponse).collect(Collectors.toList());
    }
}
