package pl.kurs.geometricfigures.validation;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import pl.kurs.geometricfigures.security.JwtUserDetailsService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

@AllArgsConstructor
public class UserNotExistsValidator implements ConstraintValidator<UserNotExists, String> {

    JwtUserDetailsService service;

    @Override
    public void initialize(UserNotExists constraintAnnotation) {

    }

    @Override
    public boolean isValid(String username, ConstraintValidatorContext constraintValidatorContext) {
        username = Optional.ofNullable(username)
                .orElse("");
        try {
            service.loadUserByUsername(username);
        } catch (UsernameNotFoundException e) {
            return true;
        }
        return false;
    }
}