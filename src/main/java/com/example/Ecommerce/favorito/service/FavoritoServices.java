package com.example.Ecommerce.favorito.service;

import java.util.List;
import java.util.Optional;

import com.example.Ecommerce.user.service.UserServices;
import org.springframework.stereotype.Service;

import com.example.Ecommerce.anuncio_produto.entity.Anuncio;
import com.example.Ecommerce.anuncio_produto.repositorie.AnuncioRepository;
import com.example.Ecommerce.favorito.entity.Favorito;
import com.example.Ecommerce.favorito.repositorie.FavoritoRepository;
import com.example.Ecommerce.user.entity.User;

@Service
public class FavoritoServices {

    private final FavoritoRepository favoritoRepository;

    private final UserServices userServices;

    private final AnuncioRepository anuncioRepository;

    public FavoritoServices(FavoritoRepository favoritoRepository,
                            UserServices userServices,
                            AnuncioRepository anuncioRepository) {

        this.favoritoRepository = favoritoRepository;
        this.userServices = userServices;
        this.anuncioRepository = anuncioRepository;
    }

    public void addFavorito(String id) {

        Optional<Anuncio> anuncioOptional = anuncioRepository.findById(id);

        if (anuncioOptional.isPresent()) {

            Anuncio anuncio = anuncioOptional.get();

            User user = userServices.getLoggedInUser();

            Favorito newFavorito = new Favorito();

            newFavorito.setUser(user);

            newFavorito.setAnuncio(anuncio);

            favoritoRepository.save(newFavorito);

        } else {
            throw new RuntimeException("Anuncio nõa foi encontrado.");
        }
    } 

    public List<Favorito> getFavoritos() {

        User user = userServices.getLoggedInUser();

        return user.getFavoritos();

    }

    public void deleteFavorito(String id) {

        User user = userServices.getLoggedInUser();

        Favorito favOptional = favoritoRepository.findById(id).orElseThrow();

        User userFavorito = favOptional.getUser();

        if (user.equals(userFavorito)) {

            favoritoRepository.delete(favOptional);

        } else {
            throw new RuntimeException("Voce não tem permição para essa ação" +
                    ".");
        }
    }
}
