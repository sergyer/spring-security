package com.example.domain.validator;

import com.example.domain.repository.UserRepository;
import lombok.AllArgsConstructor;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@AllArgsConstructor
public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

    private final UserRepository repository;

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        return email != null && repository.findByEmail(email).isEmpty();
    }

}
