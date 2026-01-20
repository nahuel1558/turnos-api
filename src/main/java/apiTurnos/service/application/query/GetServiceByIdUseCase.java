package apiTurnos.service.application.query;

import apiTurnos.service.domain.model.ServiceItem;
import apiTurnos.service.domain.service.ServiceValidator;
import apiTurnos.service.infrastructure.mapper.ServiceMapper;
import apiTurnos.service.infrastructure.repository.ServiceQueryRepository;
import apiTurnos.service.presentation.dto.response.ServiceResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GetServiceByIdUseCase {

    private final ServiceQueryRepository queryRepository;
    private final ServiceValidator validator;
    private final ServiceMapper mapper;

    public Optional<ServiceResponseDTO> execute(GetServiceByIdQuery query) {
        // 1. Buscar servicio por id y que este activo.
        Optional<ServiceItem> service = queryRepository.findByIdAndActiveTrue(query.getIdService());

        // 2. Validar si existe y está activo
        service.ifPresent(validator::validateServiceExists);
        service.ifPresent(validator::validateServiceIsActive);

        // 3. Mapear a ResponseDTO
        return service.map(mapper::mapToServiceResponse);
    }
}