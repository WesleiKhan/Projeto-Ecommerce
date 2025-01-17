package com.example.Ecommerce.auth.service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;

@Service
public class TokenServices {

    @Value("${api.security.token.secret}")
    private String secret;

    public String generationToken(CustomUserDetails userDetails) {

        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);

            String token = JWT.create()
                        .withIssuer("auth-api")
                        .withSubject(userDetails.getUsername())
                        .withExpiresAt(genExpirationdate())
                        .sign(algorithm);
            
            return token;            
        } catch (JWTCreationException e) {
            throw new RuntimeException("Error ao gerar token " + e);
        }
    }

    private Instant genExpirationdate() {

        return LocalDateTime.now().plusHours(3).toInstant(ZoneOffset.of("-03:00"));
    }

    public String validationToken(String token) {

        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);

            return JWT.require(algorithm)
                .withIssuer("auth-api")
                .build()
                .verify(token)
                .getSubject();
                  
        } catch (JWTVerificationException e) {
            throw new RuntimeException();
        }
    }
    
}
