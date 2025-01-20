package com.example.Ecommerce.vendedor.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.Ecommerce.auth.service.CustomUserDetails;
import com.example.Ecommerce.user.entity.User;
import com.example.Ecommerce.user.exceptions.UserAlreadyExists;
import com.example.Ecommerce.user.exceptions.UserNotFound;
import com.example.Ecommerce.user.repositorie.UserRepository;
import com.example.Ecommerce.vendedor.entity.Vendedor;
import com.example.Ecommerce.vendedor.repositorie.VendedorRepository;

@Service
public class VendedorServices {

    @Autowired
    private VendedorRepository vendedorRepository;

    @Autowired
    private UserRepository userRepository;

    public Vendedor createVendedor(VendedorEntryDTO data) {

        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String userId = userDetails.getId();

        Optional<User> user = userRepository.findById(userId);

        if (user.isPresent()) {

            User infoVendedor = user.get();

            if (vendedorRepository.findByNome(infoVendedor) != null) throw new UserAlreadyExists("Usuario ja e Cadastrado como Vendedor!");

            Vendedor newVendedor = new Vendedor(data.getCpf(), data.getCnpj(), data.getNumero_telefone(), data.getRua(), data.getNumero(), data.getCidade(), data.getEstado(), data.getCep(), data.getAgencia(), data.getConta(), data.getCodigo_banco());

            newVendedor.setNome(infoVendedor);

            return vendedorRepository.save(newVendedor);

        } else {
            throw new UserNotFound();
        }
    } 
}
