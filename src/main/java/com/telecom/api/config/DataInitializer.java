package com.telecom.api.config;

import com.telecom.api.entity.User;
import com.telecom.api.repository.UserRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initializeUsers(
            UserRepository userRepository) {

        return args -> {

            if (userRepository
                    .findByUsername("admin")
                    .isEmpty()) {

                BCryptPasswordEncoder encoder =
                        new BCryptPasswordEncoder();

                User user = new User(
                        "admin",
                        encoder.encode("admin123"),
                        "USER"
                );

                userRepository.save(user);

                System.out.println(
                        "Default admin user created"
                );
            }
        };
    }
}