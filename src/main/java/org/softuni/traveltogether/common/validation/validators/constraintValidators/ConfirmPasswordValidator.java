package org.softuni.traveltogether.common.validation.validators.constraintValidators;

import org.softuni.traveltogether.common.interfaces.PasswordConfirmable;
import org.softuni.traveltogether.common.validation.annotations.ConfirmPasswordMatch;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ConfirmPasswordValidator implements ConstraintValidator<ConfirmPasswordMatch, PasswordConfirmable> {

    @Override
    public boolean isValid(PasswordConfirmable passwordConfirmable, ConstraintValidatorContext constraintValidatorContext) {
        return passwordConfirmable.getPassword() != null && passwordConfirmable.getPassword().equals(passwordConfirmable.getConfirmPassword());
    }
}
