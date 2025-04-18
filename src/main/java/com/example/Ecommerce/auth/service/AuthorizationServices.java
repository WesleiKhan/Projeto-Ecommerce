package com.example.Ecommerce.auth.service;

import java.util.Optional;

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

        Optional<User> user = userRepository.findByEmail(username);

        if (user.isPresent()) {

            User user1 = user.get();

            return new CustomUserDetails(user1.getId(), user1.getEmail(), user1.getTipo_user(), user1.getPassword());

        } else {

            throw new UserNotFound("Você ainda não e cadastrado, por favor realizar o cadastror antes de tentar efetuar o login!");
        }
    }
    
}
