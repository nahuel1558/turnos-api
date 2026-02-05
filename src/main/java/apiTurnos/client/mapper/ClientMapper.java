package apiTurnos.client.mapper;

import apiTurnos.client.model.Client;
import apiTurnos.client.presentation.dto.response.ClientResponse;
import apiTurnos.client.presentation.dto.response.ClientSimpleResponse;

import java.util.List;
import java.util.stream.Collectors;

public class ClientMapper {

    public ClientResponse toResponse(Client client){
        return ClientResponse.builder()
                .id(client.getId())
                .userId(client.getUserAccount().getId())
                .email(client.getEmail())
                .fullName(client.getFullName())
                .firstName(client.getUserAccount().getFirstName())
                .lastName(client.getUserAccount().getLastName())
                .phone(client.getUserAccount().getPhone())
                .clientSince(client.getClientSince())
                .userStatus(client.getUserAccount().getStatus())
                .build();
    }

    public ClientSimpleResponse toSimpleResponse(Client client){
        return ClientSimpleResponse.builder()
                .id(client.getId())
                .userId(client.getUserAccount().getId())
                .fullName(client.getFullName())
                .email(client.getEmail())
                .phone(client.getUserAccount().getPhone())
                .build();
    }


    public List<ClientResponse> toResponseList(List<Client> clientList){
        return clientList.stream().map(this::toResponse).collect(Collectors.toList());
    }

    public List<ClientSimpleResponse> toSimpleResponseList(List<Client> clientList){
        return clientList.stream().map(this::toSimpleResponse).collect(Collectors.toList());
    }
}
