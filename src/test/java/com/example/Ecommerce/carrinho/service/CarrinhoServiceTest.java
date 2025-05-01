package com.example.Ecommerce.carrinho.service;

import com.example.Ecommerce.anuncio_produto.entity.Anuncio;
import com.example.Ecommerce.anuncio_produto.exceptions.AnuncioNotFound;
import com.example.Ecommerce.anuncio_produto.repositorie.AnuncioRepository;
import com.example.Ecommerce.interacoes_do_usuario.carrinho.entity.Carrinho;
import com.example.Ecommerce.interacoes_do_usuario.carrinho.repositorie.CarrinhoRepository;
import com.example.Ecommerce.interacoes_do_usuario.carrinho.service.CarrinhoEntryDTO;
import com.example.Ecommerce.interacoes_do_usuario.carrinho.service.CarrinhoService;
import com.example.Ecommerce.user.entity.User;
import com.example.Ecommerce.user.exceptions.UserNotAutorization;
import com.example.Ecommerce.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class CarrinhoServiceTest {

    @Mock
    CarrinhoRepository carrinhoRepository;

    @Mock
    UserService userService;

    @Mock
    AnuncioRepository anuncioRepository;

    @InjectMocks
    CarrinhoService carrinhoService;

    private User loggedInUser;
    private CarrinhoEntryDTO entry;
    private Anuncio anuncio;


    @BeforeEach
    void setUp() {

        this.loggedInUser = new User("caio", "ribeiro", "caioR",
                "caio@gmail.com", LocalDate.of(1988,5, 25),
                "caio123");
        loggedInUser.setId("2");

        this.entry = new CarrinhoEntryDTO();
        entry.setQuantidade(12);

        this.anuncio = new Anuncio();
        anuncio.setId("123");

    }

    @Test
    void addCarrinho_withValidInput() {

        when(userService.getLoggedInUser()).thenReturn(loggedInUser);

        when(anuncioRepository.findById("123")).thenReturn(Optional.of(anuncio));

        carrinhoService.addCarrinho("123", entry);

        verify(userService).getLoggedInUser();

        verify(anuncioRepository).findById("123");

        verify(carrinhoRepository).save(any(Carrinho.class));
    }

    @Test
    void addCarrinho_whenInvalidInput_thorwsAnuncioNotFound() {

        when(userService.getLoggedInUser()).thenReturn(loggedInUser);

        when(anuncioRepository.findById("123")).thenReturn(Optional.empty());

        AnuncioNotFound exception = assertThrows(AnuncioNotFound.class,
                () -> carrinhoService.addCarrinho("123", entry));

        assertEquals("Anuncio não foi encontrado.", exception.getMessage());

        verify(carrinhoRepository, never()).save(any());
    }

    @Test
    void deleteItem_withValidInput() {

        when(userService.getLoggedInUser()).thenReturn(loggedInUser);

        Carrinho itemMock = mock(Carrinho.class);
        itemMock.setId("321");
        itemMock.setUser(loggedInUser);

        when(carrinhoRepository.findById("321")).thenReturn(Optional.of(itemMock));

        when(itemMock.userEquals(loggedInUser)).thenReturn(true);

        carrinhoService.deleteItem("321");

        verify(userService).getLoggedInUser();

        verify(carrinhoRepository).findById("321");

        verify(carrinhoRepository, times(1))
                .delete(itemMock);
    }

    @Test
    void deleteItem_whenInvalidInput_thorwsAnuncioNotFound() {

        when(userService.getLoggedInUser()).thenReturn(loggedInUser);

        when(carrinhoRepository.findById("321")).thenReturn(Optional.empty());

        AnuncioNotFound exception = assertThrows(AnuncioNotFound.class,
                () -> carrinhoService.deleteItem("321"));

        assertEquals("Item não foi encontrado no carrinho.",
                exception.getMessage());

        verify(carrinhoRepository, never()).delete(any());
    }

    @Test
    void deleteItem_whenDifferentUsers_thorwsUserNotAutorization() {

        when(userService.getLoggedInUser()).thenReturn(loggedInUser);

        Carrinho itemMock = mock(Carrinho.class);
        itemMock.setId("321");
        itemMock.setUser(loggedInUser);

        when(carrinhoRepository.findById("321")).thenReturn(Optional.of(itemMock));

        when(itemMock.userEquals(loggedInUser)).thenReturn(false);

        UserNotAutorization exception = assertThrows(UserNotAutorization.class,
                () -> carrinhoService.deleteItem("321"));

        assertEquals("Usuario não Autorizado.", exception.getMessage());

        verify(carrinhoRepository, never()).delete(any());
    }

}
