package com.example.Ecommerce.user.controller;

import com.example.Ecommerce.user.service.UserEntryEditDTO;
import org.springframework.beans.factory.annotation.Autowired;
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

        userServices.createUser(data);

        return ResponseEntity.ok().body("Usuario Cadastrado com sucesso!");
         
    }
    
    @PutMapping("/edit")
    public ResponseEntity<String> updateUser(@RequestBody UserEntryEditDTO data) {

        userServices.updateUser(data);

        return ResponseEntity.ok().body("Tipo de Usuario Modificado com sucesso!"); 

    }

    @DeleteMapping("/delete/{id}") 
    public ResponseEntity<String> deleteUser(@PathVariable String id) {

        userServices.deleteUser(id);

        return ResponseEntity.ok().body("Usuario deletado com sucesso!");
        
    }
}
