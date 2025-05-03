package com.example.Ecommerce.auth.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.Ecommerce.user.entity.User;
import com.example.Ecommerce.user.exceptions.UserNotFound;
import com.example.Ecommerce.user.repositorie.UserRepository;

@Service
public class AuthorizationServices implements UserDetailsService {

    private final UserRepository userRepository;

    public AuthorizationServices(UserRepository userRepository) {

        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UserNotFound("Você ainda não e cadastrado, " +
                        "por favor realizar o cadastro antes de tentar efetuar o login!"));

        return new CustomUserDetails(user.getId(), user.getEmail(),
                user.getTipo_user(), user.getPassword());


    }
    
}
