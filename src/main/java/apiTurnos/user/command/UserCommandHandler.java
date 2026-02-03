package apiTurnos.user.command;

import apiTurnos.common.exception.NotFoundException;
import apiTurnos.user.dto.request.UpdateUserRequest;
import apiTurnos.user.mapper.UserMapper;
import apiTurnos.user.model.UserAccount;
import apiTurnos.user.repository.UserCommandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Handler para Update/Delete (Command side).
 */
@Service
@RequiredArgsConstructor
public class UserCommandHandler {

    private final UserCommandRepository userCommandRepository;
    private final PasswordEncoder passwordEncoder;

    private final UserMapper userMapper = new UserMapper();

    public void handle(UpdateUserCommand command) {
        UserAccount user = userCommandRepository.findById(command.id())
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado"));

        String newHash = null;
        if (command.password() != null && !command.password().isBlank()) {
            newHash = passwordEncoder.encode(command.password());
        }

        userMapper.applyUpdates(
                user,
                new UpdateUserRequest(
                        command.email(),
                        command.firstName(),
                        command.lastName(),
                        command.password(),
                        command.role(),
                        command.status()
                ),
                newHash
        );

        userCommandRepository.save(user);
    }

    public void handle(DeleteUserCommand command) {
        UserAccount user = userCommandRepository.findById(command.id())
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado"));

        user.softDelete(java.time.LocalDateTime.now());
        userCommandRepository.save(user);
    }
}

