package apiTurnos.service.specification;

import apiTurnos.service.query.GetServicesQuery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ServiceSpecificationBuilder {

    public ServiceSpecification buildFromQuery(GetServicesQuery query){
        log.debug("Contruyendo especificaión para query: {}", query);

        return new SpecificationBuilder(query).build();
    }

    // Builder Interno.

    private static class SpecificationBuilder{
        private final GetServicesQuery query;
        private ServiceSpecification spec;

        //Constructor donde ya aplica el filtro "IncludeInactive".
        public SpecificationBuilder(GetServicesQuery query){
            this.query = query;
            this.spec = ServiceSpecification.includeInactiveIfNeeded(query.getIncludeInactive());
        }

        // Metodo "BUILD" donde se aplican los filtros.
        public ServiceSpecification build(){
            applySearchTerm();
            applyDuration();
            applyPrice();
            applyInactiveOnlyScenario();
            return spec;
        }

        // Metodo para aplicar el filtro "BUSQUEDA POR NOMBRE O DESCRIPCION".
        private void applySearchTerm(){
            if(hasText(query.getSearchTerm())){
                spec = spec.and(ServiceSpecification.constainsInNameOrDescription(query.getSearchTerm()));
                log.debug("Agregado filtro de búsqueda: {}", query.getSearchTerm());
            }
        }

        // Metodo para aplicar el filtro "MAXIMA DURACION"
        private void applyDuration(){
            if(query.getMaxDuration() != null){
                spec = spec.and(ServiceSpecification.maxDuration(query.getMaxDuration()));
                log.debug("Agregado filtro de duración máxima: {} minutos", query.getMaxDuration());
            }
        }

        // Metodo para aplicar el filtro "PRECIO ENTRE MINIMO Y MAXIMO".
        private void applyPrice(){
            if(query.getMinPrice() != null && query.getMaxPrice() != null){
                spec = spec.and(ServiceSpecification.priceBetween(query.getMinPrice(), query.getMaxPrice()));
                log.debug("Agregado filtro de precio entre: {} y {}", query.getMinPrice(), query.getMaxPrice());
            }
        }

        // Metodo para aplicar el filtro "SERVICIOS INACTIVOS"
        private void applyInactiveOnlyScenario(){
            if(isInactiveOnlyScenario()){
                spec = ServiceSpecification.isActive(false);
                log.debug("Filtrando solo servicios inactivos");
            }
        }

        // Metodo privado para ver inactivos, no hay "SearchTerm", no hay "MaxDuration", etc.
        private boolean isInactiveOnlyScenario() {
            return Boolean.TRUE.equals(query.getIncludeInactive()) &&
                    query.getSearchTerm() == null &&
                    query.getMaxDuration() == null &&
                    query.getMinPrice() == null &&
                    query.getMaxPrice() == null;
        }

        // Metodo privado para validar texto.
        private boolean hasText(String text){
            return text != null && !text.trim().isEmpty();
        }
    }
}
