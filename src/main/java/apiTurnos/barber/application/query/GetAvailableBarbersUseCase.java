package apiTurnos.barber.application.query;

import apiTurnos.barber.domain.model.Barber;
import apiTurnos.barber.infrastructure.mapper.BarberMapper;
import apiTurnos.barber.infrastructure.repository.BarberQueryRepository;
import apiTurnos.barber.infrastructure.specification.BarberSpecificationBuilder;
import apiTurnos.barber.presentation.dto.response.BarberSimpleResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GetAvailableBarbersUseCase {

    private final BarberQueryRepository barberRepository;
    private final BarberSpecificationBuilder specificationBuilder;
    private final BarberMapper mapper;

    public List<BarberSimpleResponseDTO> execute(GetAvailableBarbersQuery query) {
        // 1. Verificar que la fecha/hora sea válida
        if (query.getDateTime() == null) {
            throw new IllegalArgumentException("Fecha y hora son requeridas");
        }

        if (query.getDateTime().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("No se pueden buscar barberos disponibles en el pasado");
        }

        // 2. Construir especificación para barberos activos y disponibles
        var spec = specificationBuilder.buildAvailableBarbersSpec(query);

        // 3. Ordenar por nombre
        Sort sort = Sort.by(Sort.Direction.ASC, "displayName");

        // 4. Ejecutar consulta
        List<Barber> availableBarbers = barberRepository.findAll(spec, sort);

        // 5. Filtrar por disponibilidad horaria específica
        List<Barber> filteredBarbers = availableBarbers.stream()
                .filter(barber -> barber.isAvailableOnDay(query.getDateTime().getDayOfWeek().getValue() % 7))
                .filter(barber -> barber.isWorkingAt(query.getDateTime().toLocalTime()))
                .collect(Collectors.toList());

        // 6. Mapear a DTO simple
        return filteredBarbers.stream()
                .map(mapper::toSimpleResponseDTO)
                .collect(Collectors.toList());
    }
}