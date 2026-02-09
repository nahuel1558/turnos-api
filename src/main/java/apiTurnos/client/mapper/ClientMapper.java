package apiTurnos.client.mapper;

import apiTurnos.client.model.Client;
import apiTurnos.client.presentation.dto.response.ClientResponse;
import apiTurnos.client.presentation.dto.response.ClientSimpleResponse;

import java.util.List;

public class ClientMapper {

    public ClientResponse toResponse(Client client) {
        return ClientResponse.builder()
                .id(client.getId())
                .userId(client.getUserAccount().getId())
                .email(client.getEmail())
                .fullName(client.getFullName())
                .firstName(client.getUserAccount().getFirstName())
                .lastName(client.getUserAccount().getLastName())
                .notes(client.getNotes())
                .allergies(client.getAllergies())
                .preferredBarberId(client.getPreferredBarberId())
                .totalAppointments(client.getTotalAppointments())
                .prefersEmailNotifications(client.getPrefersEmailNotifications())
                .prefersSmsNotifications(client.getPrefersSmsNotifications())
                .active(client.getActive())
                .clientSince(client.getClientSince())
                .updatedAt(client.getUpdatedAt())
                .userStatus(client.getUserAccount().getStatus().name())
                .build();
    }

    public ClientSimpleResponse toSimpleResponse(Client client) {
        return ClientSimpleResponse.builder()
                .id(client.getId())
                .userId(client.getUserAccount().getId())
                .fullName(client.getFullName())
                .email(client.getEmail())
                .totalAppointments(client.getTotalAppointments())
                .active(client.getActive())
                .clientSince(client.getClientSince())
                .build();
    }

    public List<ClientResponse> toResponseList(List<Client> clients) {
        return clients.stream().map(this::toResponse).toList();
    }

    public List<ClientSimpleResponse> toSimpleResponseList(List<Client> clients) {
        return clients.stream().map(this::toSimpleResponse).toList();
    }
}
