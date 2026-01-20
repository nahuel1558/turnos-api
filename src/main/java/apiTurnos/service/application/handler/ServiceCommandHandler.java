package apiTurnos.service.application.handler;

import apiTurnos.service.application.command.*;
import apiTurnos.service.presentation.dto.response.ServiceResponseDTO;
import apiTurnos.service.infrastructure.mapper.ServiceMapper;
import apiTurnos.service.infrastructure.repository.ServiceCommandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ServiceCommandHandler {

    private final ServiceCommandRepository commandRepository;
    private final ServiceMapper serviceMapper;
    private final CreateServiceUseCase createServiceUseCase;
    private final UpdateServiceUseCase updateServiceUseCase;
    private final DeleteServiceUseCase deleteServiceUseCase;

        public ServiceResponseDTO handleCreate(CreateServiceCommand command) {
            log.info("Handling CreateServiceCommand: {}", command);
            try {
                return createServiceUseCase.execute(command);
            } catch (Exception e) {
                log.error("Error handling CreateServiceCommand", e);
                throw e; // Re-lanzar excepción para manejarla en el controlador
            }
        }

        public ServiceResponseDTO handleUpdate(UpdateServiceCommand command) {
            log.info("Handling UpdateServiceCommand: {}", command);
            try {
                return updateServiceUseCase.execute(command);
            } catch (Exception e) {
                log.error("Error handling UpdateServiceCommand", e);
                throw e;
            }
        }

        public void handleDelete(DeleteServiceCommand command) {
            log.info("Handling DeleteServiceCommand: {}", command);
            try {
                deleteServiceUseCase.execute(command);
            } catch (Exception e) {
                log.error("Error handling DeleteServiceCommand", e);
                throw e;
            }
        }
}
