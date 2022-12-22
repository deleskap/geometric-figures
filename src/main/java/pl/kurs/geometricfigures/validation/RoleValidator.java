package pl.kurs.geometricfigures.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;
import java.util.Optional;

public class RoleValidator implements ConstraintValidator<Role, List<String>> {
    @Override
    public void initialize(Role constraintAnnotation) {
    }

    @Override
    public boolean isValid(List<String> roles, ConstraintValidatorContext constraintValidatorContext) {
        roles = Optional.ofNullable(roles)
                .orElse(List.of());
        List<String> possibleRoles = List.of("ROLE_ADMIN", "ROLE_CREATOR");
        return possibleRoles.containsAll(roles);
    }
}