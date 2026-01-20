package apiTurnos.client.command;

import apiTurnos.client.model.Client;
import apiTurnos.client.repository.ClientCommandRepository;
import apiTurnos.user.model.SystemRole;
import apiTurnos.user.model.UserAccount;
import apiTurnos.user.repository.SystemRoleRepository;
import apiTurnos.user.repository.UserCommandRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class RegisterClientHandler {

    private final UserCommandRepository userRepository;
    private final ClientCommandRepository clientRepository;
    private final SystemRoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserAccount handle(RegisterClientCommand command) {
        log.info("Processing RegisterClientCommand for email: {}", command.getEmail());

        // Validar email único
        if (userRepository.existsByEmail(command.getEmail())) {
            throw new IllegalArgumentException("Email ya registrado");
        }

        // Crear UserAccount
        UserAccount user = UserAccount.builder()
                .email(command.getEmail())
                .password(passwordEncoder.encode(command.getPassword()))
                .firstName(command.getFirstName())
                .lastName(command.getLastName())
                .phone(command.getPhone())
                .enabled(true)
                .build();

        // Asignar rol CLIENT
        SystemRole clientRole = roleRepository.findByName("ROLE_CLIENT")
                .orElseGet(() -> createRole("ROLE_CLIENT", "Cliente del sistema"));
        user.addRole(clientRole);

        // Guardar UserAccount
        UserAccount savedUser = userRepository.save(user);

        // Crear Client profile
        Client client = Client.builder()
                .userAccount(savedUser)
                .prefersEmailNotifications(command.getPrefersEmailNotifications())
                .prefersSmsNotifications(command.getPrefersSmsNotifications())
                .active(true)
                .build();

        clientRepository.save(client);

        log.info("Client registered successfully: ID={}, UserID={}",
                client.getId(), savedUser.getId());

        return savedUser;
    }

    private SystemRole createRole(String name, String description) {
        return roleRepository.save(
                SystemRole.builder()
                        .name(name)
                        .description(description)
                        .build()
        );
    }
}