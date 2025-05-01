package com.example.Ecommerce.interacoes_do_usuario.favorito.service;

import java.util.List;

import com.example.Ecommerce.anuncio_produto.exceptions.AnuncioNotFound;
import com.example.Ecommerce.user.exceptions.UserNotAutorization;
import com.example.Ecommerce.user.service.UserService;
import org.springframework.stereotype.Service;

import com.example.Ecommerce.anuncio_produto.entity.Anuncio;
import com.example.Ecommerce.anuncio_produto.repositorie.AnuncioRepository;
import com.example.Ecommerce.interacoes_do_usuario.favorito.entity.Favorito;
import com.example.Ecommerce.interacoes_do_usuario.favorito.repositorie.FavoritoRepository;
import com.example.Ecommerce.user.entity.User;

@Service
public class FavoritoService {

    private final FavoritoRepository favoritoRepository;

    private final UserService userService;

    private final AnuncioRepository anuncioRepository;

    public FavoritoService(FavoritoRepository favoritoRepository,
                           UserService userService,
                           AnuncioRepository anuncioRepository) {

        this.favoritoRepository = favoritoRepository;
        this.userService = userService;
        this.anuncioRepository = anuncioRepository;
    }

    public void addItem(String id) {

        User user = userService.getLoggedInUser();

        Anuncio anuncio = anuncioRepository.findById(id)
                .orElseThrow(AnuncioNotFound::new);

        Favorito newFavorito = new Favorito();

        newFavorito.setUser(user);

        newFavorito.setAnuncio(anuncio);

        favoritoRepository.save(newFavorito);

    } 

    public List<Favorito> getFavoritos() {

        User user = userService.getLoggedInUser();

        return user.getFavoritos();

    }

    public void deleteItem(String id) {

        User user = userService.getLoggedInUser();

        Favorito item = favoritoRepository.findById(id)
                .orElseThrow(() -> new AnuncioNotFound("O Anuncio " +
                        "Favoritado não foi encontrado."));

        if (!item.userEquals(user)) {
            throw new UserNotAutorization();
        }

        favoritoRepository.delete(item);

    }
}
