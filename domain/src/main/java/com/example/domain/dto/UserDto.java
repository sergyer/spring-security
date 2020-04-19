package com.example.domain.dto;

import com.example.domain.validator.PasswordConfirmed;
import com.example.domain.validator.PasswordPolicy;
import com.example.domain.validator.UniqueEmail;
import com.example.domain.validator.UniqueUsername;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@PasswordConfirmed
public class UserDto {

    @NotEmpty(message="Please enter your firstname")
    private String firstname;
    @NotEmpty(message="Please enter your lastname")
    private String lastname;
    @NotEmpty(message="Please enter a username")
    @UniqueUsername
    private String username;
    @NotEmpty(message="Please enter an email")
    @UniqueEmail
    @Email(message="Email is not valid")
    private String email;
    @NotEmpty(message="Please enter in a password")
    @PasswordPolicy
    private String password;
    @NotEmpty(message="Please confirm your password")
    private String confirmPassword;

}
