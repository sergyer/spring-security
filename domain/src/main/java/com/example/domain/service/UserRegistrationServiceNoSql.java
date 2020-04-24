package com.example.domain.service;

import com.example.domain.dto.UserDto;
import com.example.domain.model.Portfolio;
import com.example.domain.model.User;
import com.example.domain.repository.PortfolioRepository;
import com.example.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UserRegistrationServiceNoSql {

    private final UserRepository userRepository;

    private final PortfolioRepository portfolioRepository;

    public void createUser(UserDto user) {
        User cryptUser = new User(UUID.randomUUID().toString()
                , user.getUsername(),
                user.getFirstname(),
                user.getLastname(),
                "USER",
                user.getEmail(),
                user.getPassword());
        userRepository.save(cryptUser);
        portfolioRepository.save(new Portfolio(user.getUsername(), new ArrayList<>()));
    }

}
