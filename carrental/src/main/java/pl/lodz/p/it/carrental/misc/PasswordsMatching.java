package pl.lodz.p.it.carrental.misc;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PasswordsValidator.class)
@Target( { ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface PasswordsMatching {
    String message() default "Hasła nie są zgodne";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}