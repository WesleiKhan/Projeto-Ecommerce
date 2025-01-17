package com.example.Ecommerce.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Ecommerce.auth.service.CustomUserDetails;
import com.example.Ecommerce.auth.service.TokenServices;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthControllers {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenServices tokenServices;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid LoginDTO data) {

        var usernamePassword = new UsernamePasswordAuthenticationToken(data.getEmail(), data.getPassword());

        var auth = authenticationManager.authenticate(usernamePassword);

        String token = tokenServices.generationToken((CustomUserDetails) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }
    
}
