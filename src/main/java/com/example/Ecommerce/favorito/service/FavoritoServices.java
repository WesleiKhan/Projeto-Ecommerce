package com.example.Ecommerce.favorito.service;

import java.util.List;
import java.util.Optional;

import com.example.Ecommerce.anuncio_produto.exceptions.AnuncioNotFound;
import com.example.Ecommerce.user.exceptions.UserNotAutorization;
import com.example.Ecommerce.user.service.UserService;
import org.springframework.stereotype.Service;

import com.example.Ecommerce.anuncio_produto.entity.Anuncio;
import com.example.Ecommerce.anuncio_produto.repositorie.AnuncioRepository;
import com.example.Ecommerce.favorito.entity.Favorito;
import com.example.Ecommerce.favorito.repositorie.FavoritoRepository;
import com.example.Ecommerce.user.entity.User;

@Service
public class FavoritoServices {

    private final FavoritoRepository favoritoRepository;

    private final UserService userService;

    private final AnuncioRepository anuncioRepository;

    public FavoritoServices(FavoritoRepository favoritoRepository,
                            UserService userService,
                            AnuncioRepository anuncioRepository) {

        this.favoritoRepository = favoritoRepository;
        this.userService = userService;
        this.anuncioRepository = anuncioRepository;
    }

    public void addFavorito(String id) {

        Optional<Anuncio> anuncioOptional = anuncioRepository.findById(id);

        if (anuncioOptional.isPresent()) {

            Anuncio anuncio = anuncioOptional.get();

            User user = userService.getLoggedInUser();

            Favorito newFavorito = new Favorito();

            newFavorito.setUser(user);

            newFavorito.setAnuncio(anuncio);

            favoritoRepository.save(newFavorito);

        } else {
            throw new AnuncioNotFound();
        }
    } 

    public List<Favorito> getFavoritos() {

        User user = userService.getLoggedInUser();

        return user.getFavoritos();

    }

    public void deleteFavorito(String id) {

        User user = userService.getLoggedInUser();

        Favorito favOptional = favoritoRepository.findById(id)
                .orElseThrow(() -> new AnuncioNotFound("O Anuncio " +
                        "Favoritado não foi encontrado."));

        User userFavorito = favOptional.getUser();

        if (user.equals(userFavorito)) {

            favoritoRepository.delete(favOptional);

        } else {
            throw new UserNotAutorization();
        }
    }
}
