package apiTurnos.barber.application.query;

import apiTurnos.barber.domain.dto.BarberFiltersDTO;
import apiTurnos.barber.domain.model.Barber;
import apiTurnos.barber.infrastructure.mapper.BarberMapper;
import apiTurnos.barber.infrastructure.repository.BarberQueryRepository;
import apiTurnos.barber.infrastructure.specification.BarberSpecificationBuilder;
import apiTurnos.barber.presentation.dto.response.BarberSimpleResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class GetBarbersUseCase {

    private final BarberQueryRepository barberRepository;
    private final BarberSpecificationBuilder specificationBuilder;
    private final BarberMapper mapper;

    public List<BarberSimpleResponseDTO> execute(GetBarbersQuery query) {
        log.info("Executing GetBarbersUseCase with query: {}", query);

        // 1. Crear DTO de filtros
        BarberFiltersDTO filters = BarberFiltersDTO.builder()
                .searchTerm(query.getSearchTerm())
                .specialty(query.getSpecialty())
                .active(query.getActive())
                .professionalStatus(query.getProfessionalStatus())
                .build();

        // 2. Construir especificación
        var spec = specificationBuilder.buildFromQuery(filters);

        // 3. Ordenar
        Sort sort = Sort.by(Sort.Direction.ASC, "displayName");

        // 4. Ejecutar consulta
        List<Barber> barbers = barberRepository.findAll(spec, sort);

        // 5. Mapear
        return barbers.stream()
                .map(mapper::toSimpleResponseDTO)
                .collect(Collectors.toList());
    }
}