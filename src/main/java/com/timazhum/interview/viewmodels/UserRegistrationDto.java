package com.timazhum.interview.viewmodels;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class UserRegistrationDto {
    @Pattern(regexp = "^[a-z0-9]+$", message = "Only lowercase latin characters and numerals are allowed.")
    @NotEmpty(message = "Username should not be empty.")
    @Size(max = 20, message = "Username should not have more than 20 characters.")
    private String username;

    @Email(message = "Only valid email addresses are allowed.")
    @NotEmpty(message = "Email should not be empty.")
    @Size(max = 60, message = "Email should not have more than 60 characters.")
    private String email;

    @NotEmpty(message = "Password should not be empty.")
    @Size(max = 60, message = "Password should not have more than 60 characters.")
    private String password;

    @AssertTrue(message = "You should accept terms and conditions.")
    private Boolean terms;

    public UserRegistrationDto() {
    }

    public UserRegistrationDto(@NotEmpty String username, @Email @NotEmpty String email, @NotEmpty String password, @AssertTrue Boolean terms) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.terms = terms;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getTerms() {
        return terms;
    }

    public void setTerms(Boolean terms) {
        this.terms = terms;
    }
}
