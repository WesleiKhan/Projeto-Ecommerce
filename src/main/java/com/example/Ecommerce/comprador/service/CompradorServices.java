package com.example.Ecommerce.comprador.service;

import com.example.Ecommerce.user.service.UserServices;
import org.springframework.stereotype.Service;

import com.example.Ecommerce.comprador.entity.Comprador;
import com.example.Ecommerce.comprador.repositorie.CompradorRepository;
import com.example.Ecommerce.user.entity.User;
import com.example.Ecommerce.user.exceptions.UserAlreadyExists;



@Service
public class CompradorServices {

    private final CompradorRepository compradorRepository;

    private final UserServices userServices;

    public CompradorServices(CompradorRepository compradorRepository,
                             UserServices userServices) {

        this.compradorRepository = compradorRepository;
        this.userServices = userServices;
    }

    public void createComprador(CompradorEntryDTO data) {

        User infoComprador = userServices.getLoggedInUser();

        if (compradorRepository.findByNome(infoComprador).isPresent()) {
            throw new UserAlreadyExists("Usuario ja e Cadastrado como Comprador!"); }

        Comprador newComprador = new Comprador(data.getCpf(),
                data.getNumero_telefone(), data.getRua(), data.getNumero(),
                data.getCidade(), data.getEstado(), data.getCep());

        newComprador.setNome(infoComprador);

        compradorRepository.save(newComprador);

    }
    
}
