package apiTurnos.client.command;

import apiTurnos.client.model.Client;
import apiTurnos.client.repository.ClientCommandRepository;
import apiTurnos.common.exception.NotFoundException;
import apiTurnos.user.model.UserAccount;
import apiTurnos.user.repository.UserCommandRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class RegisterClientHandler {

    private final UserCommandRepository userRepository;
    private final ClientCommandRepository clientRepository;

    @Transactional
    public Client handle(RegisterClientCommand command) {
        log.info("RegisterClientCommand for userId={}", command.getUserId());

        UserAccount user = userRepository.findById(command.getUserId())
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado"));

        if (clientRepository.existsByUserAccount_Id(command.getUserId())) {
            throw new IllegalArgumentException("El usuario ya tiene un perfil de cliente");
        }

        Client client = Client.builder()
                .userAccount(user)
                .notes(command.getNotes())
                .allergies(command.getAllergies() == null ? new java.util.HashSet<>() : new java.util.HashSet<>(command.getAllergies()))
                .preferredBarberId(command.getPreferredBarberId())
                .prefersEmailNotifications(command.getPrefersEmailNotifications() != null ? command.getPrefersEmailNotifications() : true)
                .prefersSmsNotifications(command.getPrefersSmsNotifications() != null ? command.getPrefersSmsNotifications() : false)
                .active(true)
                .build();

        Client saved = clientRepository.save(client);

        log.info("Client created: clientId={}, userId={}", saved.getId(), user.getId());
        return saved;
    }
}
