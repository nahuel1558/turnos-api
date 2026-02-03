package apiTurnos.user.controller;

import apiTurnos.common.dto.IdResponse;
import apiTurnos.user.command.*;
import apiTurnos.user.dto.request.RegisterUserRequest;
import apiTurnos.user.dto.request.UpdateUserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller Command: endpoints de escritura.
 */
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserCommandController {

    private final RegisterUserUseCase registerUserUseCase;
    private final UserCommandHandler userCommandHandler;

    @PostMapping
    public ResponseEntity<IdResponse> register(@RequestBody RegisterUserRequest request) {

        String id = registerUserUseCase.handle(
                new RegisterUserCommand(
                        request.email(),
                        request.firstName(),
                        request.lastName(),
                        request.password(),
                        request.role()
                )
        );

        return ResponseEntity.ok(new IdResponse(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable String id, @RequestBody UpdateUserRequest request) {

        userCommandHandler.handle(
                new UpdateUserCommand(
                        id,
                        request.email(),
                        request.firstName(),
                        request.lastName(),
                        request.password(),
                        request.role(),
                        request.status()
                )
        );

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        userCommandHandler.handle(new DeleteUserCommand(id));
        return ResponseEntity.noContent().build();
    }
}

