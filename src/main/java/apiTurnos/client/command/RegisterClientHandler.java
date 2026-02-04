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

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class RegisterClientHandler {

    private final UserCommandRepository userRepository;
    private final ClientCommandRepository clientRepository;


    @Transactional
    public Client handle(RegisterClientCommand command) {
        log.info("Processing RegisterClientCommand for user ID: {}", command.getUserId());

        // 1. Verificar que el usuario existe
        UserAccount user = userRepository.findById(command.getUserId())
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado"));

        // 2. Verificar que el usuario no sea ya cliente
        if (clientRepository.existsByUserAccountId(command.getUserId())) {
            throw new IllegalArgumentException("El usuario ya tiene un perfil de cliente");
        }

        // 3. Crear Client profile
        Client client = Client.builder()
                .userAccount(user)
                .notes(command.getNotes())
                .allergies(command.getAllergies())
                .preferredBarberId(command.getPreferredBarberId())
                .prefersEmailNotifications(command.getPrefersEmailNotifications())
                .prefersSmsNotifications(command.getPrefersSmsNotifications())
                .active(true)
                .build();

        // 4. Guardar
        Client savedClient = clientRepository.save(client);

        log.info("Client profile created successfully: ClientID={}, UserID={}",
                savedClient.getId(), user.getId());

        return savedClient;
    }
}