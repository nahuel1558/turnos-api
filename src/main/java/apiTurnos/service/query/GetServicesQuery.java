package apiTurnos.service.query;

import lombok.*;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor @NoArgsConstructor
public class GetServicesQuery {

    private Boolean includeInactive;
    private String searchTerm;
    private Integer maxDuration;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;

    public static GetServicesQuery allActive(){
        return GetServicesQuery.builder()
                .includeInactive(false)
                .build();
    }
}
