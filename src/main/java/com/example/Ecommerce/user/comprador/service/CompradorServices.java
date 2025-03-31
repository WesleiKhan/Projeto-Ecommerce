package com.example.Ecommerce.user.comprador.service;

import com.example.Ecommerce.user.exceptions.UserNotFound;
import com.example.Ecommerce.user.service.UserServices;
import org.springframework.stereotype.Service;

import com.example.Ecommerce.user.comprador.entity.Comprador;
import com.example.Ecommerce.user.comprador.repositorie.CompradorRepository;
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

    public void updateComprador(CompradorEntryEditDTO data) {
        User user = userServices.getLoggedInUser();

        Comprador newComprador = compradorRepository.findByNome(user)
                        .orElseThrow(() -> new UserNotFound("Comprador não foi encontrado."));

        /*A Logica Para ve se os valores atribuidos a os metodos sets
        são null ou blank esta dentros dos metodos sets da entidade.*/

        newComprador.setNumero_telefone(data.getNumeroTelefone());
        newComprador.setRua(data.getRua());
        newComprador.setNumero(data.getNumero());
        newComprador.setCidade(data.getCidade());
        newComprador.setEstado(data.getEstado());
        newComprador.setCep(data.getCep());


        compradorRepository.save(newComprador);
    }

    public void deleteComprador() {

        User user = userServices.getLoggedInUser();

        Comprador comprador = compradorRepository.findByNome(user)
                        .orElseThrow(() -> new UserNotFound("Comprador não foi encontrado."));

        user.setCadastro_comprador(null);

        compradorRepository.delete(comprador);
    }
    
}
