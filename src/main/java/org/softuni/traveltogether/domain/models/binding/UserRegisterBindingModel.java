package org.softuni.traveltogether.domain.models.binding;

import org.softuni.traveltogether.common.interfaces.PasswordConfirmable;
import org.softuni.traveltogether.common.validation.annotations.ConfirmPasswordMatch;
import org.softuni.traveltogether.common.validation.annotations.Email;
import org.softuni.traveltogether.common.validation.annotations.Password;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@ConfirmPasswordMatch
public class UserRegisterBindingModel implements PasswordConfirmable {
    private String username;
    private String password;
    private String confirmPassword;
    private String email;
    private String firstName;
    private String lastName;
    private String phoneNumber;

    public UserRegisterBindingModel() {
    }

    @NotNull
    @Size(min = 3, max = 20, message = "Should be between 3 and 20 symbols")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Password(minLength = 3, maxLength = 20)
    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    @Email
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @NotNull
    @Size(min=0, message = "{org.softuni.travel-together.validation.Field.empty}")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @NotNull
    @Size(min=0, message = "{org.softuni.travel-together.validation.Field.empty}")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Pattern(regexp = "[0-9]{0,10}", message = "{org.softuni.travel-together.validation.PhoneNumber.default}")
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
