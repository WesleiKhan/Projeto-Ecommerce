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
import com.example.Ecommerce.utils.service.StripeConnectServices;
import com.example.Ecommerce.vendedor.entity.Vendedor;
import com.example.Ecommerce.vendedor.repositorie.VendedorRepository;
import com.stripe.exception.StripeException;

@Service
public class VendedorServices {

    @Autowired
    private VendedorRepository vendedorRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StripeConnectServices stripeConnectServices;

    public Vendedor createVendedor(VendedorEntryDTO data) throws StripeException{

        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String userId = userDetails.getId();

        Optional<User> user = userRepository.findById(userId);

        if (user.isPresent()) {

            User infoVendedor = user.get();

            if (vendedorRepository.findByNome(infoVendedor).isPresent()) {throw new UserAlreadyExists("Usuario ja e Cadastrado como Vendedor!");}

            String id_stripe = stripeConnectServices.criarContaVendedorStripe(infoVendedor.getEmail(), infoVendedor.getPrimeiro_nome(), infoVendedor.getSobrenome(), data.getCpf(), data.getNumero_telefone(), data.getConta(), data.getAgencia(), data.getCodigo_banco(), data.getRua(), data.getNumero(), data.getCidade(), data.getEstado(), data.getCep());

            Vendedor newVendedor = new Vendedor(data.getCpf(), data.getCnpj(), data.getNumero_telefone(), data.getRua(), data.getNumero(), data.getCidade(), data.getEstado(), data.getCep(), data.getAgencia(), data.getConta(), data.getCodigo_banco());

            newVendedor.setNome(infoVendedor);

            newVendedor.setId_account_stripe(id_stripe);

            return vendedorRepository.save(newVendedor);

        } else {
            throw new UserNotFound();
        }
    } 
}
