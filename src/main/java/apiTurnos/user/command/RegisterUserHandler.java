package apiTurnos.user.command;

import apiTurnos.common.exception.NotFoundException;
import apiTurnos.user.mapper.UserMapper;
import apiTurnos.user.model.UserAccount;
import apiTurnos.user.repository.UserCommandRepository;
import apiTurnos.user.repository.UserQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Handler de registro (Command).
 */
@Service
@RequiredArgsConstructor
public class RegisterUserHandler implements RegisterUserUseCase {

    private final UserCommandRepository userCommandRepository;
    private final UserQueryRepository userQueryRepository;
    private final PasswordEncoder passwordEncoder;

    private final UserMapper userMapper = new UserMapper();

    @Override
    public String handle(RegisterUserCommand command) {

        // Regla: email único (Query repo).
        userQueryRepository.findByEmail(command.email())
                .ifPresent(u -> { throw new IllegalArgumentException("Ya existe un usuario con ese email"); });

        // Hash password (infra/security)
        String hash = passwordEncoder.encode(command.password());

        UserAccount user = userMapper.toNewEntity(
                new apiTurnos.user.dto.request.RegisterUserRequest(
                        command.email(),
                        command.firstName(),
                        command.lastName(),
                        command.phone(),
                        command.password(),
                        command.role()
                ),
                hash
        );

        userCommandRepository.save(user);
        return user.getId();
    }
}

