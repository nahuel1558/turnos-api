package apiTurnos.barber.infrastructure.specification;

import apiTurnos.barber.application.query.GetAvailableBarbersQuery;
import apiTurnos.barber.domain.dto.BarberFiltersDTO;
import apiTurnos.barber.domain.model.Barber;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Component
public class BarberSpecificationBuilder {

    public Specification<Barber> buildFromQuery(BarberFiltersDTO filters) {
        if (filters == null) {
            filters = BarberFiltersDTO.empty();
        }

        return Specification.where(isActive(filters.getActive()))
                .and(hasSearchTerm(filters.getSearchTerm()))
                .and(hasSpecialty(filters.getSpecialty()))
                .and(hasProfessionalStatus(filters.getProfessionalStatus()));
    }

    private Specification<Barber> isActive(Boolean active) {
        return (root, query, cb) -> {
            if (active == null) return null;
            return cb.equal(root.get("active"), active);
        };
    }

    private Specification<Barber> hasSearchTerm(String searchTerm) {
        return (root, query, cb) -> {
            if (searchTerm == null || searchTerm.trim().isEmpty()) return null;
            String likePattern = "%" + searchTerm.toLowerCase() + "%";
            return cb.or(
                    cb.like(cb.lower(root.get("displayName")), likePattern),
                    cb.like(cb.lower(root.get("professionalTitle")), likePattern),
                    cb.like(cb.lower(root.get("bio")), likePattern)
            );
        };
    }

    private Specification<Barber> hasSpecialty(String specialty) {
        return (root, query, cb) -> {
            if (specialty == null || specialty.trim().isEmpty()) return null;
            return cb.isMember(specialty, root.get("specialties"));
        };
    }

    private Specification<Barber> hasProfessionalStatus(Barber.ProfessionalStatus status) {
        return (root, query, cb) -> {
            if (status == null) return null;
            return cb.equal(root.get("professionalStatus"), status);
        };
    }

    public Specification<Barber> buildAvailableBarbersSpec(GetAvailableBarbersQuery query) {
        if (query == null) {
            throw new IllegalArgumentException("Query cannot be null");
        }

        return Specification.where(isActive(true))
                .and(worksOnDay(query.getDateTime().getDayOfWeek()))
                .and(isAvailableAtTime(query.getDateTime().toLocalTime()))
                .and(hasSpecialtyIfRequired(query.getSpecialty()));
    }


    private Specification<Barber> worksOnDay(DayOfWeek dayOfWeek) {
        return (root, query, cb) -> {
            if (dayOfWeek == null) return null;
            return cb.isMember(dayOfWeek, root.get("workDays"));
        };
    }

    private Specification<Barber> isAvailableAtTime(LocalTime time) {
        return (root, query, cb) -> {
            if (time == null) return null;

            // Verifica que el barbero esté trabajando a esa hora
            return cb.and(
                    cb.lessThanOrEqualTo(root.get("workStartTime"), time),
                    cb.greaterThanOrEqualTo(root.get("workEndTime"), time)
            );
        };
    }

    private Specification<Barber> hasSpecialtyIfRequired(String specialty) {
        return (root, query, cb) -> {
            if (specialty == null || specialty.trim().isEmpty()) return null;
            return cb.isMember(specialty, root.get("specialties"));
        };
    }
}