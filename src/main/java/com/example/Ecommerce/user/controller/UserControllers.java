package com.example.Ecommerce.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Ecommerce.user.service.EditTypeUserDTO;
import com.example.Ecommerce.user.service.UserEntryDTO;
import com.example.Ecommerce.user.service.UserServices;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserControllers {

    @Autowired
    private UserServices userServices;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@Valid @RequestBody UserEntryDTO data) {

        try {
            userServices.createUser(data);

            return ResponseEntity.ok().body("Usuario Cadastrado com sucesso!");
        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e);
        }
    }
    
    @PutMapping("/edit/{id}")
    public ResponseEntity<String> updateUser(@PathVariable String id, @RequestBody EditTypeUserDTO data) {

        try {
            userServices.updateUser(id, data);

            return ResponseEntity.ok().body("Tipo de Usuario Modificado com sucesso!");
        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e);
        }
    }

    @DeleteMapping("/delete/{id}") 
    public ResponseEntity<String> deleteUser(@PathVariable String id) {

        try {
            userServices.deleteUser(id);

            return ResponseEntity.ok().body("Usuario deletado com sucesso!");
        } catch (Exception e) {
            
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e);
        }
    }
}
