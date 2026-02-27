package apiTurnos.barber.infrastructure.specification;

import apiTurnos.barber.domain.model.Barber;
import org.springframework.data.jpa.domain.Specification;


public interface BarberSpecification extends Specification<Barber> {

    static BarberSpecification isActive(Boolean active) {
        return (root, query, cb) -> {
            if (active == null) return cb.conjunction();
            return active ? cb.isTrue(root.get("active")) : cb.isFalse(root.get("active"));
        };
    }

    static BarberSpecification hasProfessionalStatus(Barber.ProfessionalStatus status) {
        return (root, query, cb) -> {
            if (status == null) return cb.conjunction();
            return cb.equal(root.get("professionalStatus"), status);
        };
    }

    static BarberSpecification containsInDisplayNameOrBio(String searchTerm) {
        return (root, query, cb) -> {
            if (searchTerm == null || searchTerm.trim().isEmpty()) {
                return cb.conjunction();
            }
            String likePattern = "%" + searchTerm.toLowerCase() + "%";
            return cb.or(
                    cb.like(cb.lower(root.get("displayName")), likePattern),
                    cb.like(cb.lower(root.get("bio")), likePattern)
            );
        };
    }

    static BarberSpecification hasSpecialty(String specialty) {
        return (root, query, cb) -> {
            if (specialty == null || specialty.trim().isEmpty()) {
                return cb.conjunction();
            }
            return cb.isMember(specialty.trim(), root.get("specialties"));
        };
    }

    static BarberSpecification isAvailable() {
        return (root, query, cb) -> cb.and(
                cb.isTrue(root.get("active")),
                cb.equal(root.get("professionalStatus"), Barber.ProfessionalStatus.AVAILABLE)
        );
    }

    default BarberSpecification and(BarberSpecification other) {
        return (root, query, cb) -> {
            var thisPredicate = this.toPredicate(root, query, cb);
            var otherPredicate = other.toPredicate(root, query, cb);
            return cb.and(thisPredicate, otherPredicate);
        };
    }
}