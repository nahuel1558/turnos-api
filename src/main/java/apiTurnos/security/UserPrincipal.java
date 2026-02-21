package apiTurnos.security;

import apiTurnos.user.model.SystemRole;
import apiTurnos.user.model.UserAccount;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
public class UserPrincipal implements UserDetails {

    private final UserAccount user;

    public UserAccount getUser() {
        return user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<SystemRole> roles = user.getRoles();

        // Si todavía no tiene roles asignados, no rompemos seguridad: ROLE_USER por defecto
        if (roles == null || roles.isEmpty()) {
            return List.of(new SimpleGrantedAuthority("ROLE_USER"));
        }

        return roles.stream()
                .map(SystemRole::getName)               // ej: "ADMIN" o "ROLE_ADMIN"
                .filter(name -> name != null && !name.isBlank())
                .map(name -> name.startsWith("ROLE_") ? name : "ROLE_" + name)
                .map(SimpleGrantedAuthority::new)
                .toList();
    }

    @Override public String getPassword() { return user.getPasswordHash(); }
    @Override public String getUsername() { return user.getEmail(); }

    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled() { return !user.isDeleted(); }
}
