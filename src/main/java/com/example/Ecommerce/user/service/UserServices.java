package com.example.Ecommerce.user.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


import com.example.Ecommerce.user.entity.User;
import com.example.Ecommerce.user.exceptions.UserAlreadyExists;
import com.example.Ecommerce.user.exceptions.UserNotFound;
import com.example.Ecommerce.user.repositorie.UserRepository;

@Service
public class UserServices {

    @Autowired
    private UserRepository userRepository;

    public User createUser(UserEntryDTO data) {

        if (userRepository.findByEmail(data.getEmail()).isPresent()) { throw new UserAlreadyExists();}

        String encryptPassword = new BCryptPasswordEncoder().encode(data.getPassword());

        User newUser = new User(data.getPrimeiro_nome(), data.getSobrenome(), data.getUsername(), data.getEmail(), data.getData_nascimento(), encryptPassword);

        return userRepository.save(newUser);
    }

    public User updateUser(String id, EditTypeUserDTO tipoUser) {

        Optional<User> user = userRepository.findById(id);

        if (user.isPresent()) {
            
            User newUser = user.get();

            newUser.setTipo_user(tipoUser.getTipo_user());

            return userRepository.save(newUser);

        } else {

            throw new UserNotFound();
        }
        
    }

    public void deleteUser(String id) {

        Optional<User> user = userRepository.findById(id);

        if (user.isPresent()) {

            User userToBeDelete = user.get();

            userRepository.delete(userToBeDelete);

        } else {
            throw new UserNotFound();
        }
    }
    
}
