package com.example.Ecommerce.user.comprador.service;

import com.example.Ecommerce.user.exceptions.UserNotFound;
import com.example.Ecommerce.user.service.UserService;
import org.springframework.stereotype.Service;

import com.example.Ecommerce.user.comprador.entity.Comprador;
import com.example.Ecommerce.user.comprador.repositorie.CompradorRepository;
import com.example.Ecommerce.user.entity.User;
import com.example.Ecommerce.user.exceptions.UserAlreadyExists;


@Service
public class CompradorService {

    private final CompradorRepository compradorRepository;

    private final UserService userService;

    public CompradorService(CompradorRepository compradorRepository,
                            UserService userService) {

        this.compradorRepository = compradorRepository;
        this.userService = userService;
    }

    public void createComprador(CompradorEntryDTO data) {

        User infoComprador = userService.getLoggedInUser();

        if (compradorRepository.findByNome(infoComprador).isPresent()) {
            throw new UserAlreadyExists("Usuario ja e Cadastrado como Comprador!"); }

        Comprador newComprador = new Comprador(data.getCpf(),
                data.getNumero_telefone(), data.getEndereco());

        newComprador.setNome(infoComprador);

        compradorRepository.save(newComprador);

    }

    public void updateComprador(CompradorEntryEditDTO data) {
        User user = userService.getLoggedInUser();

        Comprador newComprador = compradorRepository.findByNome(user)
                        .orElseThrow(() -> new UserNotFound("Comprador não foi encontrado."));

        newComprador.atualizarDados(data);

        compradorRepository.save(newComprador);
    }

    public void deleteComprador() {

        User user = userService.getLoggedInUser();

        Comprador comprador = compradorRepository.findByNome(user)
                        .orElseThrow(() -> new UserNotFound("Comprador não foi encontrado."));

        user.setCadastro_comprador(null);

        compradorRepository.delete(comprador);
    }
    
}
