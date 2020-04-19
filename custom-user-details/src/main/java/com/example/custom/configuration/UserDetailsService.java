package com.example.custom.configuration;

import com.example.domain.model.User;
import com.example.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import static org.springframework.security.core.userdetails.User.UserBuilder;
import static org.springframework.security.core.userdetails.User.builder;

@RequiredArgsConstructor
@Component
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        final User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
        final UserBuilder builder = builder();
        return builder.username(username)
                      .password(user.getPassword())
                      .roles(user.getRole())
                      .build();
    }

    @EventListener(classes = ApplicationReadyEvent.class)
    public void populate() {
        userRepository.save(new User("1", "user", "John", "Smith", "ADMIN", "john@gmail.com", "{noop}password"));
    }
}
