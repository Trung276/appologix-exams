package com.apollogix.exams.configuration.security;

import com.apollogix.exams.exception.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;

import javax.crypto.spec.SecretKeySpec;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@Slf4j
public class SecurityConfig {

    private final String[] PUBLIC_ENDPOINTS = {
            "/api/v1/auth/login",
            "/api/v1/auth/token-validation",
    };

    private final String[] COMMON_GET_ENDPOINTS = {
            "/api/v1/users/my-info",
            "/api/v1/users/exams",
            "/api/v1/exams/{examId}",
            "/api/v1/exams/search/**",
            "/api/v1/exam-submissions/**"
    };

    private final String[] COMMON_POST_ENDPOINTS = {
            "/api/v1/auth/logout",
            "/api/v1/exam-submissions",
    };

    private final String[] COMMON_PUT_ENDPOINTS = {

    };

    private final String[] TEACHER_GET_ENDPOINTS = {
            "/api/v1/users",
            "/api/v1/users/{userId}",
            "/api/v1/users/search/**",
            "/api/v1/questions",
            "/api/v1/questions/**",
            "/api/v1/answer",
            "/api/v1/answer/**"
    };

    private final String[] TEACHER_POST_ENDPOINTS = {
            "/api/v1/users",
            "/api/v1/users/email",
            "/api/v1/exams",
            "/api/v1/questions",
            "/api/v1/answer"
    };

    private final String[] TEACHER_PUT_ENDPOINTS = {
            "/api/v1/users/{userId}",
            "/api/v1/exams/{examId}",
            "/api/v1/questions/{questionId}",
            "/api/v1/answer/{answerId}"
    };

    private final String[] TEACHER_DELETE_ENDPOINTS = {
            "/api/v1/users/{userId}",
            "/api/v1/exams/{examId}",
            "/api/v1/questions/{questionId}",
            "/api/v1/answer/{answerId}"
    };

    @Value("${app.jwt.signer.key}")
    private String SIGNER_KEY;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity.authorizeHttpRequests(request ->
                request.requestMatchers(PUBLIC_ENDPOINTS).permitAll()
                        .requestMatchers(HttpMethod.GET, COMMON_GET_ENDPOINTS).hasAnyRole("TEACHER", "STUDENT")
                        .requestMatchers(HttpMethod.POST, COMMON_POST_ENDPOINTS).hasAnyRole("TEACHER", "STUDENT")
                        .requestMatchers(HttpMethod.PUT, COMMON_PUT_ENDPOINTS).hasAnyRole("TEACHER", "STUDENT")
                        .requestMatchers(HttpMethod.GET, TEACHER_GET_ENDPOINTS).hasRole("TEACHER")
                        .requestMatchers(HttpMethod.POST, TEACHER_POST_ENDPOINTS).hasRole("TEACHER")
                        .requestMatchers(HttpMethod.PUT, TEACHER_PUT_ENDPOINTS).hasRole("TEACHER")
                        .requestMatchers(HttpMethod.DELETE, TEACHER_DELETE_ENDPOINTS).hasRole("TEACHER")
                        .anyRequest().authenticated()

        );

        httpSecurity
                .logout(logout -> logout
                        .logoutUrl("/apollogix/api/v1/auth/logout")
                        .addLogoutHandler(new SecurityContextLogoutHandler())
                );

        httpSecurity
                .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        httpSecurity
                .exceptionHandling(exceptionHandling ->
                        exceptionHandling
                                .accessDeniedHandler((request, response, accessDeniedException) -> {
                                    ErrorCode errorCode = ErrorCode.ACCESS_DENIED;
                                    log.error("Access Denied: {}", errorCode.getMessage());
                                    response.sendError(errorCode.getStatusCode().value(), errorCode.getMessage());
                                })
                );

        httpSecurity.oauth2ResourceServer(oauth2 ->
                oauth2.jwt(jwtConfigurer ->
                                jwtConfigurer.decoder(jwtDecoder())
                                        .jwtAuthenticationConverter(jwtAuthenticationConverter()))
                        .authenticationEntryPoint(new JwtAuthenticationEntryPoint())
        );

        httpSecurity.csrf(AbstractHttpConfigurer::disable);

        return httpSecurity.build();
    }


    @Bean
    JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        jwtGrantedAuthoritiesConverter.setAuthorityPrefix("ROLE_");

        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);

        return jwtAuthenticationConverter;
    }

    @Bean
    JwtDecoder jwtDecoder() {
        SecretKeySpec secretKeySpec = new SecretKeySpec(SIGNER_KEY.getBytes(), "HS512");
        return NimbusJwtDecoder
                .withSecretKey(secretKeySpec)
                .macAlgorithm(MacAlgorithm.HS512)
                .build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }


}

