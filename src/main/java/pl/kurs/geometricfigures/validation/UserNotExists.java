package pl.kurs.geometricfigures.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = UserNotExistsValidator.class)
@Target({FIELD, PARAMETER, ANNOTATION_TYPE})
@Retention(RUNTIME)
public @interface UserNotExists {
    String message() default "PROVIDED_USER_EXISTS";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
