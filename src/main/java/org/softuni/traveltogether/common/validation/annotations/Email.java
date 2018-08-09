package org.softuni.traveltogether.common.validation.annotations;

import org.softuni.traveltogether.common.validation.validators.constraintValidators.EmailValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;


@Target({FIELD, METHOD, PARAMETER})
@Retention(RUNTIME)
@Constraint(validatedBy = EmailValidator.class)
public @interface Email {

    String message() default "{org.softuni.travel-together.validation.Email.default}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
