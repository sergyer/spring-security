package com.example.domain.validator;

import com.example.domain.repository.UserRepository;
import lombok.AllArgsConstructor;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@AllArgsConstructor
public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername, String> {

    private final UserRepository repository;

    @Override
    public boolean isValid(String username, ConstraintValidatorContext context) {
        return username != null && repository.findByUsername(username).isEmpty();
    }

}
