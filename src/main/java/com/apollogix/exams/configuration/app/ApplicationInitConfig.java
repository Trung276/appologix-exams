package com.apollogix.exams.configuration.app;


import com.apollogix.exams.entity.User;
import com.apollogix.exams.enums.Role;
import com.apollogix.exams.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;

@Configuration
@Slf4j
public class ApplicationInitConfig {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Value("${app.default.account}")
    private String DEFAULT_ACCOUNT;

    @Bean
    ApplicationRunner applicationRunner() {
        return args -> {
            if (userRepository.findByEmail(DEFAULT_ACCOUNT).isEmpty()) {

                User user = User.builder()
                        .email(DEFAULT_ACCOUNT)
                        .password(passwordEncoder.encode("12345678"))
                        .fullName("TEACHER APOLLOGIX")
                        .dateOfBirth(new Date())
                        .role(Role.TEACHER)
                        .build();

                userRepository.save(user);
                log.warn("Default teacher has been created with email: teacher@example.com && password: 12345678");
            }
        };
    }

}
