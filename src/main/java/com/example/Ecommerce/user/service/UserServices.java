package com.example.Ecommerce.user.service;

import java.util.Optional;

import com.example.Ecommerce.auth.service.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
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

        String encryptPassword =
                new BCryptPasswordEncoder().encode(data.getPassword().trim());

        User newUser = new User(data.getPrimeiro_nome().trim(),
                data.getSobrenome().trim(), data.getUsername().trim(),
                data.getEmail().trim(), data.getData_nascimento(), encryptPassword);

        return userRepository.save(newUser);
    }

    public User updateUser(UserEntryEditDTO data) {

        CustomUserDetails userDetails =
                (CustomUserDetails) SecurityContextHolder
                        .getContext().getAuthentication().getPrincipal();

        String userId = userDetails.getId();

        Optional<User> userOptional = userRepository.findById(userId);

        if (userOptional.isPresent()) {
            
            User newUser = userOptional.get();

            if(data.getPrimeiroNome() != null && !data.getPrimeiroNome()
                    .trim().isEmpty()) {

                newUser.setPrimeiro_nome(data.getPrimeiroNome());
            }
            if(data.getSobrenome() != null && !data.getSobrenome()
                    .trim().isEmpty()) {

                newUser.setSobrenome(data.getSobrenome());
            }
            if(data.getUsername() != null && !data.getUsername()
                    .trim().isEmpty()) {

                newUser.setUsername(data.getUsername());
            }
            if(data.getDataNascimento() != null) {

                newUser.setData_nascimento(data.getDataNascimento());
            }
            if(data.getPassword() != null && !data.getPassword()
                    .trim().isEmpty()) {

                String passwordEncrypt =
                        new BCryptPasswordEncoder().encode(data.getPassword());

                newUser.setPassword(passwordEncrypt);
            }

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
