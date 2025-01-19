package com.example.Ecommerce.favorito.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.Ecommerce.anuncio_produto.entity.Anuncio;
import com.example.Ecommerce.anuncio_produto.repositorie.AnuncioRepository;
import com.example.Ecommerce.auth.service.CustomUserDetails;
import com.example.Ecommerce.favorito.entity.Favorito;
import com.example.Ecommerce.favorito.repositorie.FavoritoRepository;
import com.example.Ecommerce.user.entity.User;
import com.example.Ecommerce.user.repositorie.UserRepository;

@Service
public class FavoritoServices {

    @Autowired
    private FavoritoRepository favoritoRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AnuncioRepository anuncioRepository;

    public Favorito addFavorito(String id) {

        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String userid = userDetails.getId();

        Anuncio anuncio = anuncioRepository.findById(id).orElseThrow();

        Optional<User> user = userRepository.findById(userid);

        if (user.isPresent()) {

            User user2 = user.get();

            Favorito newFavorito = new Favorito();

            newFavorito.setUser(user2);

            newFavorito.setAnuncio(anuncio);

            return favoritoRepository.save(newFavorito);

        } else {
            throw new RuntimeException();
        }
    } 

    public List<Favorito> getFavoritos() {

        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String userId = userDetails.getId();

        Optional<User> user = userRepository.findById(userId);

        if (user.isPresent()) {

            User user2 = user.get();

            return user2.getFavoritos();

        } else {
            throw new RuntimeException();
        }
    }
}
