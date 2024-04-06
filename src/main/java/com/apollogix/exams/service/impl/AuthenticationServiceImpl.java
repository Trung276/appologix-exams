package com.apollogix.exams.service.impl;

import com.apollogix.exams.dto.request.auth.AuthenticationRequest;
import com.apollogix.exams.dto.request.auth.TokenValidationRequest;
import com.apollogix.exams.dto.response.auth.AuthenticationResponse;
import com.apollogix.exams.dto.response.auth.TokenValidationResponse;
import com.apollogix.exams.entity.TokenBlacklist;
import com.apollogix.exams.entity.User;
import com.apollogix.exams.exception.AppException;
import com.apollogix.exams.exception.ErrorCode;
import com.apollogix.exams.repository.TokenBlacklistRepository;
import com.apollogix.exams.repository.UserRepository;
import com.apollogix.exams.service.AuthenticationService;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.util.Date;

@Service
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenBlacklistRepository tokenBlacklistRepository;

    @Value("${app.jwt.signer.key}")
    private String SIGNER_KEY;

    @Value("${app.jwt.expiration.time}")
    private String EXPIRATION_TIME;

    @Override
    public TokenValidationResponse validToken(TokenValidationRequest request) {
        String token = request.getToken();

        boolean isValid = isTokenValid(token);
        boolean isInBlacklist = tokenBlacklistRepository.existsByToken(token);

        if (!isValid || isInBlacklist) {
            return TokenValidationResponse.builder()
                    .isValidToken(false)
                    .message("Token has expired")
                    .build();
        }

        return TokenValidationResponse.builder()
                .isValidToken(true)
                .message("Token is valid")
                .build();
    }

    @Override
    public AuthenticationResponse login(AuthenticationRequest request) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        User user = userRepository.findByEmailAndIsActivatedTrue(request.getEmail())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        boolean isAuthenticated = passwordEncoder.matches(request.getPassword(), user.getPassword());

        if (!isAuthenticated) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        String token = generateToken(user);

        return AuthenticationResponse.builder()
                .token(token)
                .tokenType("Bearer")
                .expiresTime(EXPIRATION_TIME)
                .build();
    }

    @Override
    public String logout(HttpServletRequest request) {
        String token = getTokenFromRequest(request);
        if (token == null) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        addToBlacklist(token);

        return "Logout success!";
    }

    @Override
    public boolean isTokenValid(String token) {
        try {
            JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());

            SignedJWT signedJWT = SignedJWT.parse(token);

            Date expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();

            return expiryTime != null && expiryTime.after(new Date()) && signedJWT.verify(verifier);
        } catch (ParseException e) {
            log.error("Cannot parse token", e);
            throw new AppException(ErrorCode.TOKEN_GENERATION_FAILED);
        } catch (JOSEException e) {
            log.error("Cannot verify token", e);
            throw new AppException(ErrorCode.TOKEN_GENERATION_FAILED);
        }
    }

    @Override
    public void addToBlacklist(String token) {
        TokenBlacklist tokenBlacklist = new TokenBlacklist();
        tokenBlacklist.setToken(token);

        tokenBlacklistRepository.save(tokenBlacklist);
    }

    @Override
    public boolean isTokenInBlacklist(String token) {
        return tokenBlacklistRepository.existsByToken(token);
    }

    private String generateToken(User user) {
        String token;
        boolean isInBlacklist;
        int maxRetry = 3;

        for (int i = 0; i < maxRetry; i++) {
            JWSObject jwsObject = generateJwsObject(user);
            token = jwsObject.serialize();
            isInBlacklist = isTokenInBlacklist(token);

            if (!isInBlacklist) {
                return token;
            }
        }

        throw new AppException(ErrorCode.TOKEN_GENERATION_FAILED);
    }

    private JWSObject generateJwsObject(User user) {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

        long expirationMillis = System.currentTimeMillis() + Long.parseLong(EXPIRATION_TIME);

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getEmail())
                .issuer("apollogix.com")
                .issueTime(new Date())
                .expirationTime(new Date(expirationMillis))
                .claim("scope", buildScope(user))
                .build();

        Payload payload = new Payload(jwtClaimsSet.toJSONObject());

        JWSObject jwsObject = new JWSObject(header, payload);

        try {
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
            return jwsObject;
        } catch (JOSEException e) {
            log.error("Cannot create token", e);
            throw new AppException(ErrorCode.TOKEN_GENERATION_FAILED);
        }
    }

    private String buildScope(User user) {
        if (user != null && user.getRole() != null) {
            return user.getRole().toString();
        }
        return "";
    }

    private String getTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

}
