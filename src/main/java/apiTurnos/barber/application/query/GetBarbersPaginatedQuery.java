package apiTurnos.barber.application.query;

import apiTurnos.common.dto.PaginatedRequest;
import lombok.*;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetBarbersPaginatedQuery {

    private PaginatedRequest pagination;
    private String searchTerm;
    private String specialty;
    private Boolean active;

    public static GetBarbersPaginatedQuery fromParams(
            PaginatedRequest pagination,
            String searchTerm,
            String specialty,
            Boolean active) {

        return GetBarbersPaginatedQuery.builder()
                .pagination(pagination)
                .searchTerm(searchTerm)
                .specialty(specialty)
                .active(active)
                .build();
    }
}