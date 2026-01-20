package apiTurnos.service.application.handler;

import apiTurnos.service.application.query.GetServiceByIdUseCase;
import apiTurnos.service.application.query.GetServicesUseCase;
import apiTurnos.service.presentation.dto.response.ServiceResponseDTO;
import apiTurnos.service.application.query.GetServiceByIdQuery;
import apiTurnos.service.application.query.GetServicesQuery;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class ServiceQueryHandler {

    private final GetServicesUseCase getServicesUseCase;
    private final GetServiceByIdUseCase getServiceByIdUseCase;

    public List<ServiceResponseDTO> handle(GetServicesQuery query) {
        log.info("Handling GetServicesQuery: {}", query);
        try {
            return getServicesUseCase.execute(query);
        } catch (Exception e) {
            log.error("Error handling GetServicesQuery", e);
            throw e;
        }
    }

    public Optional<ServiceResponseDTO> handle(GetServiceByIdQuery query) {
        log.info("Handling GetServiceByIdQuery: {}", query);
        try {
            return getServiceByIdUseCase.execute(query);
        } catch (Exception e) {
            log.error("Error handling GetServiceByIdQuery", e);
            throw e;
        }
    }
}
