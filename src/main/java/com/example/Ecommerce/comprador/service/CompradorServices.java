package com.example.Ecommerce.comprador.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.Ecommerce.auth.service.CustomUserDetails;
import com.example.Ecommerce.comprador.entity.Comprador;
import com.example.Ecommerce.comprador.repositorie.CompradorRepository;
import com.example.Ecommerce.user.entity.User;
import com.example.Ecommerce.user.exceptions.UserAlreadyExists;
import com.example.Ecommerce.user.exceptions.UserNotFound;
import com.example.Ecommerce.user.repositorie.UserRepository;

@Service
public class CompradorServices {

    @Autowired
    private CompradorRepository compradorRepository;

    @Autowired
    private UserRepository userRepository;

    public Comprador createComprador(CompradorEntryDTO data) {

        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String userId = userDetails.getId();

        Optional<User> user = userRepository.findById(userId);

        if (user.isPresent()) {

            User infoComprador = user.get();

            if (compradorRepository.findByNome(infoComprador).isPresent()) { throw new UserAlreadyExists("Usuario ja e Cadastrado como Comprador!"); }

            Comprador newComprador = new Comprador(data.getCpf(), data.getNumero_telefone(), data.getRua(), data.getNumero(), data.getCidade(), data.getEstado(), data.getCep());

            newComprador.setNome(infoComprador);

            return compradorRepository.save(newComprador);

        } else {

            throw new UserNotFound();
        }

    }
    
}
