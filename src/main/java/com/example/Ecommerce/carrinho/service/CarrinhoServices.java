package com.example.Ecommerce.carrinho.service;

import java.util.List;
import java.util.Optional;

import com.example.Ecommerce.user.service.UserServices;
import org.springframework.stereotype.Service;

import com.example.Ecommerce.anuncio_produto.entity.Anuncio;
import com.example.Ecommerce.anuncio_produto.repositorie.AnuncioRepository;
import com.example.Ecommerce.carrinho.entity.Carrinho;
import com.example.Ecommerce.carrinho.repositorie.CarrinhoRepository;
import com.example.Ecommerce.user.entity.User;


@Service
public class CarrinhoServices {

    private final CarrinhoRepository carrinhoRepository;

    private final UserServices userServices;

    private final AnuncioRepository anuncioRepository;

    public CarrinhoServices(CarrinhoRepository carrinhoRepository,
                            UserServices userServices,
                            AnuncioRepository anuncioRepository) {

        this.carrinhoRepository = carrinhoRepository;
        this.userServices = userServices;
        this.anuncioRepository = anuncioRepository;
    }

    public void addCarrinho(String id, CarrinhoEntryDTO data) {

        Optional<Anuncio> anunOptional = anuncioRepository.findById(id);

        if (anunOptional.isPresent()) {

            Anuncio anuncio = anunOptional.get();

            User user = userServices.getLoggedInUser();

            Carrinho carrinho = new Carrinho(data.getQuantidade());

            carrinho.setUser(user);

            carrinho.setAnuncio(anuncio);

            carrinhoRepository.save(carrinho);

        } else {
            throw new RuntimeException();
        }
    }

    public List<Carrinho> getCarrinhos() {

        User user = userServices.getLoggedInUser();

        return user.getCarrinhos();

    }

    public void deleteCarrinho(String id) {

        User user = userServices.getLoggedInUser();

        Carrinho carrinho = carrinhoRepository.findById(id).orElseThrow();

        User user2 = carrinho.getUser();

        if (user.equals(user2)) {

            carrinhoRepository.delete(carrinho);
            
        } else {
            throw new RuntimeException();
        }
    }
    
}
