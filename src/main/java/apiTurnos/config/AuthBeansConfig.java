package apiTurnos.config;

import apiTurnos.security.UserPrincipal;
import apiTurnos.user.model.UserAccount;
import apiTurnos.user.repository.UserQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
@RequiredArgsConstructor
public class AuthBeansConfig {

    private final UserQueryRepository userQueryRepository;

    @Bean
    public UserDetailsService userDetailsService() {
        return email -> {
            UserAccount user = userQueryRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado: " + email));
            return new UserPrincipal(user);
        };
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        // ✅ Esto evita el quilombo de DaoAuthenticationProvider manual
        return config.getAuthenticationManager();
    }
}


