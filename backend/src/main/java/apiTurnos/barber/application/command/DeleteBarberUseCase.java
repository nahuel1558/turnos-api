package apiTurnos.barber.application.command;

import apiTurnos.barber.domain.model.Barber;
import apiTurnos.barber.domain.service.BarberValidator;
import apiTurnos.barber.infrastructure.repository.BarberCommandRepository;
import apiTurnos.common.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional
public class DeleteBarberUseCase {

    private final BarberCommandRepository repository;
    private final BarberValidator validator;

    public void execute(DeleteBarberCommand command){
        // 1. Buscar Barber por ID.
        Barber barber = repository.findById(command.getIdBarber())
                .orElseThrow(() -> new NotFoundException("No se encontro el Peluquero"));

        // Validar que existe Barber.
        validator.validateBarberExists(barber);

        // Eliminar/Desactivar (soft delete)
        barber.deactivate();

        // Guardar en la base de datos.
        repository.save(barber);
    }
}
