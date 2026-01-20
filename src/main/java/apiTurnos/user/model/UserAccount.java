package apiTurnos.user.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "user_accounts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserAccount implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 120)
    private String email;

    @Column(nullable = false, length = 200)
    private String password;  // Encriptada

    @Column(name = "first_name", nullable = false, length = 80)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 80)
    private String lastName;

    @Column(length = 30)
    private String phone;

    // ROLES - Relación con SystemRole
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_account_roles",
            joinColumns = @JoinColumn(name = "user_account_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    @Builder.Default
    private Set<SystemRole> roles = new HashSet<>();

    // ESTADO SPRING SECURITY
    @Column(nullable = false)
    @Builder.Default
    private boolean enabled = true;

    @Column(name = "account_non_expired", nullable = false)
    @Builder.Default
    private boolean accountNonExpired = true;

    @Column(name = "account_non_locked", nullable = false)
    @Builder.Default
    private boolean accountNonLocked = true;

    @Column(name = "credentials_non_expired", nullable = false)
    @Builder.Default
    private boolean credentialsNonExpired = true;

    // AUDITORÍA
    @Column(name = "created_at", nullable = false, updatable = false)
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "last_login")
    private LocalDateTime lastLogin;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    // === IMPLEMENTACIÓN UserDetails ===

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toSet());
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    // === COMMAND METHODS ===

    public void updateProfile(String firstName, String lastName, String phone) {
        if (firstName != null && !firstName.isBlank()) this.firstName = firstName.trim();
        if (lastName != null && !lastName.isBlank()) this.lastName = lastName.trim();
        if (phone != null) this.phone = phone.trim();
        this.updatedAt = LocalDateTime.now();
    }

    public void addRole(SystemRole role) {
        if (role != null && !this.roles.contains(role)) {
            this.roles.add(role);
            this.updatedAt = LocalDateTime.now();
        }
    }

    public void removeRole(SystemRole role) {
        if (role != null) {
            this.roles.remove(role);
            this.updatedAt = LocalDateTime.now();
        }
    }

    public void recordLogin() {
        this.lastLogin = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public void deactivate() {
        this.enabled = false;
        this.updatedAt = LocalDateTime.now();
    }

    public void activate() {
        this.enabled = true;
        this.updatedAt = LocalDateTime.now();
    }

    // === QUERY METHODS ===

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public boolean hasRole(String roleName) {
        return roles.stream().anyMatch(role -> role.getName().equals(roleName));
    }

    public boolean isClient() {
        return hasRole("ROLE_CLIENT");
    }

    public boolean isBarber() {
        return hasRole("ROLE_BARBER");
    }

    public boolean isAdmin() {
        return hasRole("ROLE_ADMIN");
    }
}