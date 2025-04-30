package com.example.Ecommerce.favorito.service;

import com.example.Ecommerce.anuncio_produto.entity.Anuncio;
import com.example.Ecommerce.anuncio_produto.exceptions.AnuncioNotFound;
import com.example.Ecommerce.anuncio_produto.repositorie.AnuncioRepository;
import com.example.Ecommerce.favorito.entity.Favorito;
import com.example.Ecommerce.favorito.repositorie.FavoritoRepository;
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
public class FavoritoServiceTest {

    @Mock
    FavoritoRepository favoritoRepository;

    @Mock
    UserService userService;

    @Mock
    AnuncioRepository anuncioRepository;

    @InjectMocks
    FavoritoService favoritoService;

    private User loggedInUser;
    private Anuncio anuncio;

    @BeforeEach
    void setUp() {

        this.loggedInUser = new User("caio", "ribeiro", "caioR",
                "caio@gmail.com", LocalDate.of(1988,5, 25),
                "caio123");
        loggedInUser.setId("1");

        this.anuncio = new Anuncio();
        anuncio.setId("123");
    }

    @Test
    void addItem_withValidInput() {

        when(userService.getLoggedInUser()).thenReturn(loggedInUser);

        when(anuncioRepository.findById("123")).thenReturn(Optional.of(anuncio));

        favoritoService.addItem("123");

        verify(userService).getLoggedInUser();

        verify(anuncioRepository).findById("123");

        verify(favoritoRepository).save(any(Favorito.class));
    }

    @Test
    void addItem_whenInvalidInput_thorwsAnuncioNotFound() {

        when(userService.getLoggedInUser()).thenReturn(loggedInUser);

        when(anuncioRepository.findById("123")).thenReturn(Optional.empty());

        AnuncioNotFound exception = assertThrows(AnuncioNotFound.class,
                () -> favoritoService.addItem("123"));

        assertEquals("Anuncio não foi encontrado.", exception.getMessage());

        verify(favoritoRepository, never()).save(any());
    }

    @Test
    void deleteItem_withValidInput() {

        when(userService.getLoggedInUser()).thenReturn(loggedInUser);

        Favorito itemMock = mock(Favorito.class);
        itemMock.setId("123");
        itemMock.setUser(loggedInUser);

        when(favoritoRepository.findById("123")).thenReturn(Optional.of(itemMock));

        when(itemMock.userEquals(loggedInUser)).thenReturn(true);

        favoritoService.deleteItem("123");

        verify(userService).getLoggedInUser();

        verify(favoritoRepository).findById("123");

        verify(favoritoRepository, times(1)).delete(itemMock);

    }

    @Test
    void deleteItem_whenInvalidInput_thorwsAnuncioNotFound() {

        when(userService.getLoggedInUser()).thenReturn(loggedInUser);

        when(favoritoRepository.findById("123")).thenReturn(Optional.empty());

        AnuncioNotFound exception = assertThrows(AnuncioNotFound.class,
                () -> favoritoService.deleteItem("123"));

        assertEquals("O Anuncio Favoritado não foi encontrado.",
                exception.getMessage());

        verify(favoritoRepository, never()).delete(any());
    }

    @Test
    void deleteItem_whenDifferentUser_thorwsUserNotAuthorization() {

        when(userService.getLoggedInUser()).thenReturn(loggedInUser);

        Favorito itemMock = mock(Favorito.class);
        itemMock.setId("123");
        itemMock.setUser(loggedInUser);

        when(favoritoRepository.findById("123")).thenReturn(Optional.of(itemMock));

        when(itemMock.userEquals(loggedInUser)).thenReturn(false);

        UserNotAutorization exception = assertThrows(UserNotAutorization.class,
                () -> favoritoService.deleteItem("123"));

        assertEquals("Usuario não Autorizado.", exception.getMessage());

        verify(favoritoRepository, never()).delete(any());
    }
}
