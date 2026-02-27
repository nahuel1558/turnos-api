package apiTurnos.barber.application.handler;

import apiTurnos.barber.application.command.*;
import apiTurnos.barber.application.query.*;
import apiTurnos.barber.presentation.dto.response.BarberResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
@Slf4j
public class BarberCommandHandler {

    private final RegisterBarberUseCase registerBarberUseCase;
    private final UpdateBarberUseCase updateBarberUseCase;
    private final ChangeBarberStatusUseCase changeBarberStatusUseCase;
    private final DeleteBarberUseCase deleteBarberUseCase;

    public BarberResponseDTO handleRegister(RegisterBarberCommand command) {
        log.info("Handling RegisterBarberCommand: {}", command);
        try {
            return registerBarberUseCase.execute(command);
        } catch (Exception e) {
            log.error("Error handling RegisterBarberCommand", e);
            throw e;
        }
    }

    public BarberResponseDTO handleUpdate(UpdateBarberCommand command) {
        log.info("Handling UpdateBarberCommand: {}", command);
        try {
            return updateBarberUseCase.execute(command);
        } catch (Exception e) {
            log.error("Error handling UpdateBarberCommand", e);
            throw e;
        }
    }

    public void handleChangeStatus(ChangeBarberStatusCommand command) {
        log.info("Handling ChangeBarberStatusCommand: {}", command);
        try {
            changeBarberStatusUseCase.execute(command);
        } catch (Exception e) {
            log.error("Error handling ChangeBarberStatusCommand", e);
            throw e;
        }
    }

    public void handleDelete(DeleteBarberCommand command) {
        log.info("Handling DeleteBarberCommand: {}", command);
        try {
            deleteBarberUseCase.execute(command);
        } catch (Exception e) {
            log.error("Error handling DeleteBarberCommand", e);
            throw e;
        }
    }
}