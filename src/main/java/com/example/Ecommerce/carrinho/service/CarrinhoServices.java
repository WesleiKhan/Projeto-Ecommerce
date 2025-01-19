package com.example.Ecommerce.carrinho.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.Ecommerce.anuncio_produto.entity.Anuncio;
import com.example.Ecommerce.anuncio_produto.repositorie.AnuncioRepository;
import com.example.Ecommerce.auth.service.CustomUserDetails;
import com.example.Ecommerce.carrinho.entity.Carrinho;
import com.example.Ecommerce.carrinho.repositorie.CarrinhoRepository;
import com.example.Ecommerce.user.entity.User;
import com.example.Ecommerce.user.repositorie.UserRepository;

@Service
public class CarrinhoServices {

    @Autowired
    private CarrinhoRepository carrinhoRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AnuncioRepository anuncioRepository;

    public Carrinho addCarrinho(String id, CarrinhoEntryDTO data) {

        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String userId = userDetails.getId();

        Optional<User> userOptional = userRepository.findById(userId);

        Anuncio anuncio = anuncioRepository.findById(id).orElseThrow();

        if (userOptional.isPresent()) {

            User user = userOptional.get();

            Carrinho carrinho = new Carrinho(data.getQuantidade());

            carrinho.setUser(user);

            carrinho.setAnuncio(anuncio);

            return carrinhoRepository.save(carrinho);

        } else {
            throw new RuntimeException();
        }
    }

    public List<Carrinho> getCarrinhos() {

        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String userId = userDetails.getId();

        Optional<User> userOptional = userRepository.findById(userId);

        if (userOptional.isPresent()) {

            User user = userOptional.get();

            return user.getCarrinhos();

        } else {
            throw new RuntimeException();
        }
    }

    public void deleteCarrinho(String id) {

        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String userId = userDetails.getId();

        User user = userRepository.findById(userId).orElseThrow();

        Carrinho carrinho = carrinhoRepository.findById(id).orElseThrow();

        User user2 = carrinho.getUser();

        if (user.equals(user2)) {

            carrinhoRepository.delete(carrinho);
            
        } else {
            throw new RuntimeException();
        }
    }
    
}
