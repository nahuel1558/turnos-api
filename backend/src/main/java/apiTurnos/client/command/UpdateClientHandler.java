package apiTurnos.client.command;

import apiTurnos.client.model.Client;
import apiTurnos.client.repository.ClientCommandRepository;
import apiTurnos.common.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UpdateClientHandler {

    private final ClientCommandRepository clientRepository;

    @Transactional
    public Client handle(UpdateClientCommand command) {

        Client client = clientRepository.findById(command.getClientId())
                .orElseThrow(() -> new NotFoundException("Cliente no encontrado"));

        // Notas
        if (command.getNotes() != null) {
            client.setNotes(command.getNotes());
        }

        // Alergias (si viene null, no toca; si viene vacío, lo deja vacío)
        if (command.getAllergies() != null) {
            client.getAllergies().clear();
            client.getAllergies().addAll(command.getAllergies());
        }

        // Preferencia de barbero
        if (command.getPreferredBarberId() != null) {
            client.setPreferredBarberId(command.getPreferredBarberId());
        }

        // Preferencias de notificación
        if (command.getPrefersEmailNotifications() != null) {
            client.setPrefersEmailNotifications(command.getPrefersEmailNotifications());
        }
        if (command.getPrefersSmsNotifications() != null) {
            client.setPrefersSmsNotifications(command.getPrefersSmsNotifications());
        }

        // Estado activo
        if (command.getActive() != null) {
            client.setActive(command.getActive());
        }

        Client saved = clientRepository.save(client);
        log.info("Client updated clientId={}", saved.getId());
        return saved;
    }
}
