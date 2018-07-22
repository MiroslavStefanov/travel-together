package org.softuni.traveltogether.domain.models.binding;

import org.softuni.traveltogether.common.validation.annotations.Email;
import org.softuni.traveltogether.common.validation.annotations.Password;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserBindingModel {
    private String username;
    private String password;
    private String confirmPassword;
    private String email;

    public UserBindingModel() {
    }

    @NotNull
    @Size(min = 3, max = 20)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Password(minLength = 5, maxLength = 20)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

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
}
