package com.example.Ecommerce.user.controller;

import com.example.Ecommerce.user.service.UserEntryEditDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Ecommerce.user.service.UserEntryDTO;
import com.example.Ecommerce.user.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserControllers {

    private final UserService userService;

    public UserControllers(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@Valid @RequestBody UserEntryDTO data) {

        userService.createUser(data);

        return ResponseEntity.ok().body("Usuario Cadastrado com sucesso!");
         
    }
    
    @PutMapping("/edit")
    public ResponseEntity<String> updateUser(@Valid @RequestBody UserEntryEditDTO data) {

        userService.updateUser(data);

        return ResponseEntity.ok().body("Tipo de Usuario Modificado com sucesso!"); 

    }

    @DeleteMapping("/delete/{id}") 
    public ResponseEntity<String> deleteUser(@PathVariable String id) {

        userService.deleteUser(id);

        return ResponseEntity.ok().body("Usuario deletado com sucesso!");
        
    }
}
