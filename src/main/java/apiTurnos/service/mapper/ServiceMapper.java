package apiTurnos.service.mapper;

import apiTurnos.service.dto.ServiceRequestDTO;
import apiTurnos.service.dto.ServiceResponseDTO;
import apiTurnos.service.model.ServiceItem;


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
}
