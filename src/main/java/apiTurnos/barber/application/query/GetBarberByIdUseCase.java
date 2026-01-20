package apiTurnos.barber.application.query;

import apiTurnos.barber.domain.model.Barber;
import apiTurnos.barber.domain.service.BarberValidator;
import apiTurnos.barber.infrastructure.mapper.BarberMapper;
import apiTurnos.barber.infrastructure.repository.BarberQueryRepository;
import apiTurnos.barber.presentation.dto.response.BarberResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GetBarberByIdUseCase {

    private final BarberQueryRepository barberRepository;
    private final BarberValidator validator;
    private final BarberMapper mapper;

    public Optional<BarberResponseDTO> execute(GetBarberByIdQuery query) {
        // 1. Buscar barbero
        Optional<Barber> barber = barberRepository.findById(query.getId());

        // 2. Validar si existe y está activo
        barber.ifPresent(validator::validateBarberExists);
        barber.ifPresent(validator::validateBarberIsActive);

        // 3. Mapear a DTO
        return barber.map(mapper::toBarberResponse);
    }
}