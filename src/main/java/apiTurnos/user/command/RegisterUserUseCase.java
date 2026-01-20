package apiTurnos.user.command;

import apiTurnos.user.model.SystemRole;
import apiTurnos.user.model.UserAccount;
import apiTurnos.user.repository.SystemRoleRepository;
import apiTurnos.user.repository.UserCommandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// apiTurnos.user.application.command.RegisterUserUseCase.java
@Service
@RequiredArgsConstructor
public class RegisterUserUseCase {

    private final UserCommandRepository userRepository;
    private final SystemRoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserAccount execute(RegisterUserCommand command) {
        // 1. Validar email único
        if (userRepository.existsByEmail(command.getEmail())) {
            throw new IllegalArgumentException("Email ya registrado");
        }

        // 2. Crear UserAccount
        UserAccount user = UserAccount.builder()
                .email(command.getEmail())
                .password(passwordEncoder.encode(command.getPassword()))
                .firstName(command.getFirstName())
                .lastName(command.getLastName())
                .phone(command.getPhone())
                .enabled(true)
                .build();

        // 3. Asignar rol básico (CLIENT por defecto)
        SystemRole clientRole = roleRepository.findByName("ROLE_CLIENT")
                .orElseGet(() -> createRole("ROLE_CLIENT", "Cliente del sistema"));
        user.addRole(clientRole);

        // 4. Persistir
        return userRepository.save(user);
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