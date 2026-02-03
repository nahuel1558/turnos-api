package apiTurnos.user.controller;

import apiTurnos.user.dto.response.UserResponse;
import apiTurnos.user.query.GetUserByHandler;
import apiTurnos.user.query.GetUserByIdQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller Query: endpoints de consulta.
 */
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserQueryController {

    private final GetUserByHandler getUserByHandler;

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getById(@PathVariable String id) {
        return ResponseEntity.ok(getUserByHandler.handle(new GetUserByIdQuery(id)));
    }
}

