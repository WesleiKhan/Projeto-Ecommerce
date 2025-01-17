package com.example.Ecommerce.user.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.Ecommerce.auth.service.CustomUserDetails;
import com.example.Ecommerce.user.entity.User;
import com.example.Ecommerce.user.repositorie.UserRepository;

@Service
public class UserServices {

    @Autowired
    private UserRepository userRepository;

    public User createUser(UserEntryDTO data) {

        String encryptPassword = new BCryptPasswordEncoder().encode(data.getPassword());

        User newUser = new User(data.getPrimeiro_nome(), data.getSobrenome(), data.getUsername(), data.getEmail(), data.getData_nascimento(), encryptPassword);

        return userRepository.save(newUser);
    }

    public User updateUser(String id, EditTypeUserDTO tipoUser) {

        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String userId = userDetails.getId();

        if (userId.equals(id)) {

            Optional<User> user = userRepository.findById(id);

            if (user.isPresent()) {
            
                User newUser = user.get();

                newUser.setTipo_user(tipoUser.getTipo_user());

                return userRepository.save(newUser);

            } else {

                throw new RuntimeException();
            }
        }else {
            throw new RuntimeException("User não tem permissão.");
        }
    }

    public void deleteUser(String id) {

        Optional<User> user = userRepository.findById(id);

        if (user.isPresent()) {

            User userToBeDelete = user.get();

            userRepository.delete(userToBeDelete);

        } else {
            throw new RuntimeException();
        }
    }
    
}
