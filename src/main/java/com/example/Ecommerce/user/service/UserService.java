package com.example.Ecommerce.user.service;

import java.util.Optional;

import com.example.Ecommerce.auth.service.CustomUserDetails;
import com.example.Ecommerce.user.exceptions.UserNotAutorization;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


import com.example.Ecommerce.user.entity.User;
import com.example.Ecommerce.user.exceptions.UserAlreadyExists;
import com.example.Ecommerce.user.exceptions.UserNotFound;
import com.example.Ecommerce.user.repositorie.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {

        this.userRepository = userRepository;
    }

    public void createUser(UserEntryDTO data) {

        if (userRepository.findByEmail(data.getEmail()).isPresent()) { throw new UserAlreadyExists();}

        String encryptPassword =
                new BCryptPasswordEncoder().encode(data.getPassword().trim());

        User newUser = new User(data.getPrimeiro_nome().trim(),
                data.getSobrenome().trim(), data.getUsername().trim(),
                data.getEmail().trim(), data.getData_nascimento(), encryptPassword);

        userRepository.save(newUser);
    }

    public void updateUser(UserEntryEditDTO data) {
            
        User newUser = this.getLoggedInUser();

        newUser.setPrimeiro_nome(data.getPrimeiroNome());
        newUser.setSobrenome(data.getSobrenome());
        newUser.setUsername(data.getUsername());
        newUser.setData_nascimento(data.getDataNascimento());

        if(data.getPassword() != null && !data.getPassword()
                .trim().isEmpty()) {

            String passwordEncrypt = new BCryptPasswordEncoder()
                    .encode(data.getPassword());

            newUser.setPassword(passwordEncrypt);
        }

        userRepository.save(newUser);
        
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

    public User getLoggedInUser() {

        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder
                        .getContext().getAuthentication().getPrincipal();

        String userId = userDetails.getId();

        Optional<User> optionalUser = userRepository.findById(userId);

        if(optionalUser.isPresent()) {

            return optionalUser.get();

        }else {
            throw new UserNotAutorization("Não foi possivel Obter o Usuario logado.");
        }
    }
    
}
