package apiTurnos.service.application.query;

import apiTurnos.service.domain.model.ServiceItem;
import apiTurnos.service.infrastructure.mapper.ServiceMapper;
import apiTurnos.service.infrastructure.repository.ServiceQueryRepository;
import apiTurnos.service.infrastructure.specification.ServiceSpecificationBuilder;
import apiTurnos.service.presentation.dto.response.ServiceResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class GetServicesUseCase {

    private final ServiceQueryRepository queryRepository;
    private final ServiceSpecificationBuilder specificationBuilder;
    private final ServiceMapper mapper;

    public List<ServiceResponseDTO> execute(GetServicesQuery query) {
        log.info("Executing GetServicesUseCase with query: {}", query);

        // 1. Construir especificación
        var spec = specificationBuilder.buildFromQuery(query);

        // 2. Definir ordenamiento
        Sort sort = Sort.by(Sort.Direction.ASC, "name");

        // 3. Ejecutar consulta
        List<ServiceItem> serviceItems = queryRepository.findAll(spec, sort);

        log.info("Found {} services", serviceItems.size());

        // 4. Mapear a ResponseDTOs
        return mapper.mapToListServiceResponse(serviceItems);
    }
}