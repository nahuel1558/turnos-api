package apiTurnos.auth.application;

import apiTurnos.auth.infrastructure.JwtService;
import apiTurnos.auth.infrastructure.UserPrincipal;
import apiTurnos.auth.presentation.dto.LoginRequest;
import apiTurnos.auth.presentation.dto.AuthResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthResponse login(LoginRequest request) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password())
        );

        UserPrincipal principal = (UserPrincipal) auth.getPrincipal();

        String token = jwtService.generateToken(
                principal.getUsername(),
                Map.of(
                        "uid", principal.getUser().getId()
                        // si querés, también roles en claims
                )
        );

        return AuthResponse.bearer(token);
    }
}
