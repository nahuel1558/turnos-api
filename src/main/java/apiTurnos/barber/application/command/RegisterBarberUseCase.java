package apiTurnos.barber.application.command;

import apiTurnos.barber.domain.model.Barber;
import apiTurnos.barber.domain.service.BarberValidator;
import apiTurnos.barber.infrastructure.mapper.BarberMapper;
import apiTurnos.barber.infrastructure.repository.BarberCommandRepository;
import apiTurnos.barber.presentation.dto.response.BarberResponseDTO;
import apiTurnos.user.model.SystemRole;
import apiTurnos.user.model.UserAccount;
import apiTurnos.user.repository.SystemRoleRepository;
import apiTurnos.user.repository.UserQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class RegisterBarberUseCase {

    private final BarberCommandRepository barberRepository;
    private final UserQueryRepository userRepository;
    private final SystemRoleRepository roleRepository;
    private final BarberValidator validator;
    private final BarberMapper mapper;

    public BarberResponseDTO execute(RegisterBarberCommand command) {
        // 1. Validar que el usuario existe
        UserAccount userAccount = userRepository.findById(command.getIdUser())
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        // 2. Validar que el usuario no sea ya barbero
        validator.validateUserAccountNotAlreadyBarber(
                command.getIdUser(),
                barberRepository.existsByUserAccountId(command.getIdUser())
        );

        // 3. Validar unicidad del display name
        validator.validateDisplayNameUniqueness(
                command.getDisplayName(),
                barberRepository.existsByDisplayNameAndActiveTrue(command.getDisplayName())
        );

        // 3.5 Agregar rol BARBER al usuario.
        SystemRole barberRole = roleRepository.findByName("ROLE_BARBER")
                .orElseGet(() -> createBarberRole());
        userAccount.addRole(barberRole);
        userRepository.save(userAccount); // Guardar cambio de roles

        // 4. Crear barbero usando Factory Method
        Barber barber = Barber.create(
                userAccount,
                command.getDisplayName(),
                command.getProfessionalTitle(),
                command.getBio()
        );

        // 5. Configurar especialidades si existen
        if (command.getSpecialties() != null) {
            command.getSpecialties().forEach(barber::addSpecialty);
        }

        // 6. Configurar horario si se proporciona
        if (command.getWorkStartTime() != null && command.getWorkEndTime() != null) {
            barber.setSchedule(
                    command.getWorkStartTime(),
                    command.getWorkEndTime(),
                    command.getBreakStartTime(),
                    command.getBreakEndTime()
            );
        }

        // 7. Configurar días de trabajo si se proporcionan
        if (command.getWorkDays() != null) {
            barber.setWorkDays(command.getWorkDays());
        }

        // 8. Persistir
        Barber saved = barberRepository.save(barber);

        // 9. Retornar respuesta
        return mapper.toBarberResponse(saved);
    }
    private SystemRole createBarberRole() {
        return roleRepository.save(
                SystemRole.builder()
                        .name("ROLE_BARBER")
                        .build()
        );
    }
}