package apiTurnos.user.query;

import apiTurnos.common.exception.NotFoundException;
import apiTurnos.user.dto.response.UserResponse;
import apiTurnos.user.mapper.UserMapper;
import apiTurnos.user.model.UserAccount;
import apiTurnos.user.repository.UserQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Handler Query: solo lectura.
 */
@Service
@RequiredArgsConstructor
public class GetUserByHandler {

    private final UserQueryRepository userQueryRepository;
    private final UserMapper userMapper = new UserMapper();

    public UserResponse handle(GetUserByIdQuery query) {
        UserAccount user = userQueryRepository.findById(query.id())
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado"));

        return userMapper.toResponse(user);
    }
}

