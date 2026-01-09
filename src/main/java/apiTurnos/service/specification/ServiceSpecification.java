package apiTurnos.service.specification;

import apiTurnos.service.model.ServiceItem;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;

public interface ServiceSpecification extends Specification<ServiceItem> {


    // Especificacion para estado "ACTIVO" o "INACTIVO.
    static ServiceSpecification isActive(Boolean active){
        return (root, query, cb) ->{
            if(active == null) return cb.conjunction();
            return active ? cb.isTrue(root.get("active")) : cb.isFalse(root.get("active"));
        };
    }
    // Especificacion para busqueda por "NOMBRE" o "DESCRIPCION".
    static ServiceSpecification constainsInNameOrDescription(String searchTerm){
        return (root, query, cb) -> {
            if(searchTerm == null || searchTerm.trim().isEmpty()){
                return cb.conjunction();
            }
            String likePattern = "%" + searchTerm.toLowerCase() + "%";
            return cb.or(
                    cb.like(cb.lower(root.get("name")), likePattern)
            );
        };
    }

    // Especificacion para "DURACION MAXIMA".
    static ServiceSpecification maxDuration(Integer maxMinutes){
        return (root,query,cb) -> {
            if(maxMinutes == null) return cb.conjunction();
            return cb.lessThanOrEqualTo(root.get("durationMinutes"), maxMinutes);
        };
    }

    // Especificacion para rango de "PRECIO".
    static ServiceSpecification priceBetween(BigDecimal minPrice, BigDecimal maxPrice){
        return(root,query,cb) -> {
            if(minPrice == null || maxPrice == null) return cb.conjunction();
            return cb.between(root.get("price"), minPrice, maxPrice);
        };
    }

    // Especificacion para incluir "INACTIVOS".
    static ServiceSpecification includeInactiveIfNeeded(Boolean includeInactive){
        return(root, query, cb) -> {
            // Si "includeInactive" es "TRUE", incluye todos (sin filtro).
            // Si es "FALSE" o "NULL", solo "ACTIVOS"
            if(Boolean.TRUE.equals(includeInactive)){
                return cb.conjunction();
            }
            return cb.isTrue(root.get("active")); //Solo activos.
        };
    }

    // Metodo para convinar las especificaciones con "AND".
    default ServiceSpecification and(ServiceSpecification other){
        return (root, query, cb) -> {
            Predicate thisPredicate = this.toPredicate(root, query, cb);
            Predicate otherPredicate = other.toPredicate(root, query, cb);
            return cb.and(thisPredicate,otherPredicate);
        };
    }
}
