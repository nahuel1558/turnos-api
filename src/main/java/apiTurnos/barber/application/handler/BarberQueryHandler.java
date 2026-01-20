package apiTurnos.barber.application.handler;

import apiTurnos.barber.application.query.*;
import apiTurnos.barber.presentation.dto.response.BarberResponseDTO;
import apiTurnos.barber.presentation.dto.response.BarberSimpleResponseDTO;
import apiTurnos.common.dto.PaginatedResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class BarberQueryHandler {

    private final GetBarbersUseCase getBarbersUseCase;
    private final GetBarberByIdUseCase getBarberByIdUseCase;
    private final GetAvailableBarbersUseCase getAvailableBarbersUseCase;
    private final GetBarbersPaginatedUseCase getBarbersPaginatedUseCase;

    public List<BarberSimpleResponseDTO> handle(GetBarbersQuery query) {
        log.info("Handling GetBarbersQuery: {}", query);
        try {
            return getBarbersUseCase.execute(query);
        } catch (Exception e) {
            log.error("Error handling GetBarbersQuery", e);
            throw e;
        }
    }


    // Para paginación
    public PaginatedResponse<BarberSimpleResponseDTO> handle(GetBarbersPaginatedQuery query) {
        return getBarbersPaginatedUseCase.execute(query);
    }


    public Optional<BarberResponseDTO> handle(GetBarberByIdQuery query) {
        log.info("Handling GetBarberByIdQuery: {}", query);
        try {
            return getBarberByIdUseCase.execute(query);
        } catch (Exception e) {
            log.error("Error handling GetBarberByIdQuery", e);
            throw e;
        }
    }

    public List<BarberSimpleResponseDTO> handle(GetAvailableBarbersQuery query) {
        log.info("Handling GetAvailableBarbersQuery: {}", query);
        try {
            return getAvailableBarbersUseCase.execute(query);
        } catch (Exception e) {
            log.error("Error handling GetAvailableBarbersQuery", e);
            throw e;
        }
    }
}