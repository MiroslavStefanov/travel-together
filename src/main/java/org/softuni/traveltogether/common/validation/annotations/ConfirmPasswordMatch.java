package org.softuni.traveltogether.common.validation.annotations;

import org.softuni.traveltogether.common.validation.validators.constraintValidators.ConfirmPasswordValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ConfirmPasswordValidator.class)
public @interface ConfirmPasswordMatch {
    String message() default "{org.softuni.travel-together.validation.Password.confirm}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
