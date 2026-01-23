package apiTurnos.user.mapper;

import apiTurnos.user.dto.response.UserResponse;
import apiTurnos.user.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserResponse toResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.getPhone(),
                user.isActive(),
                user.getRole().stream().map()
        )
    }
}
