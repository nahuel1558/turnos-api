package apiTurnos.barber.application.command;

import apiTurnos.barber.domain.model.Barber;
import apiTurnos.barber.domain.service.BarberValidator;
import apiTurnos.barber.infrastructure.mapper.BarberMapper;
import apiTurnos.barber.infrastructure.repository.BarberCommandRepository;
import apiTurnos.barber.presentation.dto.response.BarberResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UpdateBarberUseCase {

    private final BarberCommandRepository barberRepository;
    private final BarberValidator validator;
    private final BarberMapper mapper;

    public BarberResponseDTO execute(UpdateBarberCommand command) {
        // 1. Buscar barbero
        Barber barber = barberRepository.findById(command.getIdBarber())
                .orElseThrow(() -> new IllegalArgumentException("Barbero no encontrado"));

        // 2. Validar existencia y estado
        validator.validateBarberExists(barber);
        validator.validateBarberIsActive(barber);

        // 3. Validar unicidad del display name (excluyendo el actual)
        validator.validateDisplayNameForUpdate(
                command.getUpdateBarberRequest().getDisplayName(),
                command.getIdBarber(),
                barberRepository.existsByDisplayNameAndIdNotActiveTrue(
                        command.getUpdateBarberRequest().getDisplayName(),
                        command.getIdBarber()
                )
        );

        // 4. Actualizar información básica
        barber.updateInformation(
                command.getUpdateBarberRequest().getDisplayName(),
                command.getUpdateBarberRequest().getProfessionalTitle(),
                command.getUpdateBarberRequest().getBio()
        );

        // 5. Actualizar especialidades si se proporcionan
        if (command.getUpdateBarberRequest().getSpecialties() != null) {
            barber.getSpecialties().clear();
            command.getUpdateBarberRequest().getSpecialties().forEach(barber::addSpecialty);
        }

        // 6. Actualizar horario si se proporciona
        if (command.getUpdateBarberRequest().getWorkStartTime() != null && command.getUpdateBarberRequest().getWorkEndTime() != null) {
            barber.setSchedule(
                    command.getUpdateBarberRequest().getWorkStartTime(),
                    command.getUpdateBarberRequest().getWorkEndTime(),
                    command.getUpdateBarberRequest().getBreakStartTime(),
                    command.getUpdateBarberRequest().getBreakEndTime()
            );
        }

        // 7. Persistir
        Barber updated = barberRepository.save(barber);

        // 8. Retornar respuesta
        return mapper.toBarberResponse(updated);
    }
}