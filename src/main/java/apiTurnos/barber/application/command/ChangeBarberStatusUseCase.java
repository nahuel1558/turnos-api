package apiTurnos.barber.application.command;

import apiTurnos.barber.domain.model.Barber;
import apiTurnos.barber.domain.service.BarberValidator;
import apiTurnos.barber.infrastructure.repository.BarberCommandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ChangeBarberStatusUseCase {

    private final BarberCommandRepository barberRepository;
    private final BarberValidator validator;

    public void execute(ChangeBarberStatusCommand command) {
        // 1. Buscar barbero
        Barber barber = barberRepository.findById(command.getBarberId())
                .orElseThrow(() -> new IllegalArgumentException("Barbero no encontrado"));

        // 2. Validar existencia
        validator.validateBarberExists(barber);

        // 3. Cambiar estado
        barber.changeProfessionalStatus(command.getNewStatus());

        // 4. Persistir
        barberRepository.save(barber);
    }
}