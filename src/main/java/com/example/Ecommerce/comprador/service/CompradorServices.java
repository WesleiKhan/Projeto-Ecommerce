package com.example.Ecommerce.comprador.service;

import com.example.Ecommerce.user.exceptions.UserNotFound;
import com.example.Ecommerce.user.service.UserServices;
import org.springframework.stereotype.Service;

import com.example.Ecommerce.comprador.entity.Comprador;
import com.example.Ecommerce.comprador.repositorie.CompradorRepository;
import com.example.Ecommerce.user.entity.User;
import com.example.Ecommerce.user.exceptions.UserAlreadyExists;

import java.util.Optional;


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

    public void updateComprador(CompradorEntryEditDTO data) {
        User user = userServices.getLoggedInUser();

        Optional<Comprador> optiComprador = compradorRepository.findByNome(user);

        if (optiComprador.isPresent()) {

            Comprador newComprador = optiComprador.get();

            /*A Logica Para ve se os valores atribuidos a os metodos sets
            são null ou blank esta dentros dos metodos sets da entidade,
            se os valores que estão sendo atribuido forem null ou um string
            vazia o valor não sera atualizado no banco de dados.*/

            newComprador.setNumero_telefone(data.getNumeroTelefone());
            newComprador.setRua(data.getRua());
            newComprador.setNumero(data.getNumero());
            newComprador.setCidade(data.getCidade());
            newComprador.setEstado(data.getEstado());
            newComprador.setCep(data.getCep());


            compradorRepository.save(newComprador);

        } else {
            throw new UserNotFound("Comprador não foi encontrado.");
        }

    }
    
}
