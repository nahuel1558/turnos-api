package apiTurnos.client.command;

import apiTurnos.client.model.Client;
import apiTurnos.client.repository.ClientCommandRepository;
import apiTurnos.user.repository.UserCommandRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UpdateClientHandler {

    private final ClientCommandRepository clientRepository;
    private final UserCommandRepository userRepository;

    @Transactional
    public Client handle(Long clientId, UpdateClientCommand command) {
        log.info("Processing UpdateClientCommand for clientId: {}", clientId);

        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado"));

        // Actualizar preferencias
        client.updatePreferences(
                command.getNotes()
        );

        // Actualizar preferencias de notificación
        client.setNotificationPreferences(
                command.getPrefersEmailNotifications(),
                command.getPrefersSmsNotifications()
        );

        Client updatedClient = clientRepository.save(client);
        log.info("Client updated successfully: ID={}", clientId);

        return updatedClient;
    }
}