package com.example.Ecommerce.carrinho.service;

import java.util.List;
import java.util.Optional;

import com.example.Ecommerce.anuncio_produto.exceptions.AnuncioNotFound;
import com.example.Ecommerce.user.exceptions.UserNotAutorization;
import com.example.Ecommerce.user.service.UserService;
import org.springframework.stereotype.Service;

import com.example.Ecommerce.anuncio_produto.entity.Anuncio;
import com.example.Ecommerce.anuncio_produto.repositorie.AnuncioRepository;
import com.example.Ecommerce.carrinho.entity.Carrinho;
import com.example.Ecommerce.carrinho.repositorie.CarrinhoRepository;
import com.example.Ecommerce.user.entity.User;


@Service
public class CarrinhoService {

    private final CarrinhoRepository carrinhoRepository;

    private final UserService userService;

    private final AnuncioRepository anuncioRepository;

    public CarrinhoService(CarrinhoRepository carrinhoRepository,
                           UserService userService,
                           AnuncioRepository anuncioRepository) {

        this.carrinhoRepository = carrinhoRepository;
        this.userService = userService;
        this.anuncioRepository = anuncioRepository;
    }

    public void addCarrinho(String id, CarrinhoEntryDTO data) {

        User user = userService.getLoggedInUser();

        Anuncio anuncio = anuncioRepository.findById(id)
                .orElseThrow(AnuncioNotFound::new);

        Carrinho carrinho = new Carrinho(data.getQuantidade());

        carrinho.setUser(user);

        carrinho.setAnuncio(anuncio);

        carrinhoRepository.save(carrinho);


    }

    public List<Carrinho> getCarrinhos() {

        User user = userService.getLoggedInUser();

        return user.getCarrinhos();

    }

    public void deleteItem(String id) {

        User user = userService.getLoggedInUser();

        Carrinho carrinho = carrinhoRepository.findById(id)
                .orElseThrow(() -> new AnuncioNotFound("Item não foi " +
                        "encontrado no carrinho."));

        if (!carrinho.userEquals(user)) {
            throw new UserNotAutorization();
        }

        carrinhoRepository.delete(carrinho);
    }
    
}
