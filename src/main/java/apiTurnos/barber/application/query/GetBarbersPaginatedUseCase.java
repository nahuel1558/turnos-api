package apiTurnos.barber.application.query;

import apiTurnos.barber.domain.dto.BarberFiltersDTO;
import apiTurnos.barber.domain.model.Barber;
import apiTurnos.barber.infrastructure.mapper.BarberMapper;
import apiTurnos.barber.infrastructure.repository.BarberQueryRepository;
import apiTurnos.barber.infrastructure.specification.BarberSpecificationBuilder;
import apiTurnos.barber.presentation.dto.response.BarberSimpleResponseDTO;
import apiTurnos.common.dto.PaginatedResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class GetBarbersPaginatedUseCase {

    private final BarberQueryRepository barberRepository;
    private final BarberSpecificationBuilder specificationBuilder;
    private final BarberMapper mapper;

    public PaginatedResponse<BarberSimpleResponseDTO> execute(GetBarbersPaginatedQuery query) {
        log.info("Executing GetBarbersPaginatedUseCase with query: {}", query);

        // 1. Crear DTO de filtros
        BarberFiltersDTO filters = BarberFiltersDTO.builder()
                .searchTerm(query.getSearchTerm())
                .specialty(query.getSpecialty())
                .active(query.getActive())
                .build();

        // 2. Construir especificación
        var spec = specificationBuilder.buildFromQuery(filters);

        // 3. Obtener Pageable
        Pageable pageable = query.getPagination().toPageable("displayName");

        // 4. Ejecutar consulta paginada
        Page<Barber> barberPage = barberRepository.findAll(spec, pageable);

        // 5. Mapear y retornar
        Page<BarberSimpleResponseDTO> dtoPage = barberPage.map(mapper::toSimpleResponseDTO);
        return PaginatedResponse.fromPage(dtoPage);
    }
}