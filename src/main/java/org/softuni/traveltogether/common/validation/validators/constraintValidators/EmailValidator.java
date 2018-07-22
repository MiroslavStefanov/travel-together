package org.softuni.traveltogether.common.validation.validators.constraintValidators;

import org.softuni.traveltogether.common.validation.annotations.Email;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailValidator implements ConstraintValidator<Email, String> {

    private Pattern pattern;

    private static final String patternString = "^(([A-Za-z0-9]+[\\.\\-_]+)*[A-Za-z0-9]+)@((([A-Za-z]+[\\-]+)*[A-Za-z]+\\.)+([A-Za-z]+[\\-]+)*[A-Za-z]+)$";


    @Override
    public void initialize(Email email) {
        pattern = Pattern.compile(patternString);
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if(s == null)
            return false;

        Matcher matcher = this.pattern.matcher(s);

        return matcher.find();
    }
}