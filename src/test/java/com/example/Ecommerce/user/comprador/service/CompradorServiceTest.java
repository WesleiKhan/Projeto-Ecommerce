package com.example.Ecommerce.user.comprador.service;

import com.example.Ecommerce.user.comprador.entity.Comprador;
import com.example.Ecommerce.user.comprador.repositorie.CompradorRepository;
import com.example.Ecommerce.user.entity.User;
import com.example.Ecommerce.user.exceptions.UserAlreadyExists;
import com.example.Ecommerce.user.exceptions.UserNotFound;
import com.example.Ecommerce.user.objectValue.Endereco;
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
public class CompradorServiceTest {

    @Mock
    CompradorRepository compradorRepository;

    @Mock
    UserService userService;

    @InjectMocks
    CompradorService compradorService;

    private User loggedInUser;
    private Endereco endereco;
    private CompradorEntryDTO entry;
    private CompradorEntryEditDTO entryEdit;

    @BeforeEach
    void setUp() {

        this.loggedInUser = new User("caio", "ribeiro", "caioR",
                "caio@gmail.com", LocalDate.of(1988,5, 25),
                "caio123");

        this.endereco = new Endereco();
        endereco.setRua("rua exemplo");
        endereco.setNumero("123");
        endereco.setCidade("cidade exemplo");
        endereco.setEstado("estado exemplo");
        endereco.setCep("12345000");

        this.entry = new CompradorEntryDTO();
        entry.setCpf("12345678998");
        entry.setNumero_telefone("89542612387");
        entry.setEndereco(endereco);

        this.entryEdit = new CompradorEntryEditDTO();
        entryEdit.setNumeroTelefone("89542612387");
        entryEdit.setEndereco(endereco);

    }

    @Test
    void createComprador_withValidInput() {

        when(userService.getLoggedInUser()).thenReturn(loggedInUser);

        when(compradorRepository.findByNome(loggedInUser)).thenReturn(Optional.empty());

        compradorService.createComprador(entry);

        verify(userService).getLoggedInUser();

        verify(compradorRepository).findByNome(loggedInUser);

        verify(compradorRepository).save(any(Comprador.class));
    }

    @Test
    void createComprador_whenUserIsAlreadyABuyer_throwsUserAlreadyExists() {

        when(userService.getLoggedInUser()).thenReturn(loggedInUser);

        Comprador comprador = new Comprador();
        comprador.setNome(loggedInUser);

        when(compradorRepository.findByNome(loggedInUser)).thenReturn(Optional.of(comprador));

        UserAlreadyExists exception = assertThrows(UserAlreadyExists.class,
                () -> compradorService.createComprador(entry));

        assertEquals("Usuario ja e Cadastrado como Comprador!",
                exception.getMessage());

    }

    @Test
    void updateComprador_withValidInput() {

        when(userService.getLoggedInUser()).thenReturn(loggedInUser);

        Comprador comprador = new Comprador();
        comprador.setEndereco(endereco);
        comprador.setNome(loggedInUser);

        when(compradorRepository.findByNome(loggedInUser))
                .thenReturn(Optional.of(comprador));

        compradorService.updateComprador(entryEdit);

        verify(userService).getLoggedInUser();

        verify(compradorRepository).findByNome(loggedInUser);

        verify(compradorRepository, times(1))
                .save(any(Comprador.class));
    }

    @Test
    void updateComprador_whenInvalidUser_throwsUserNotFound() {

        when(userService.getLoggedInUser()).thenReturn(loggedInUser);

        when(compradorRepository.findByNome(loggedInUser)).thenReturn(Optional.empty());

        UserNotFound exception = assertThrows(
                UserNotFound.class,
                () -> compradorService.updateComprador(entryEdit));

        assertEquals("Comprador não foi encontrado.", exception.getMessage());

        verify(compradorRepository, never()).save(any());
    }

    @Test
    void deleteComprador_withValidInput() {

        when(userService.getLoggedInUser()).thenReturn(loggedInUser);

        Comprador comprador = new Comprador();
        comprador.setNome(loggedInUser);

        when(compradorRepository.findByNome(loggedInUser))
                .thenReturn(Optional.of(comprador));

        compradorService.deleteComprador();

        verify(userService).getLoggedInUser();

        verify(compradorRepository).findByNome(loggedInUser);

        verify(compradorRepository, times(1))
                .delete(comprador);
    }

    @Test
    void deleteComprador_whenInvalidUser_thorwsUserNotFound() {

        when(userService.getLoggedInUser()).thenReturn(loggedInUser);

        when(compradorRepository.findByNome(loggedInUser)).thenReturn(Optional.empty());

        UserNotFound exception = assertThrows(UserNotFound.class,
                () -> compradorService.deleteComprador());

        assertEquals("Comprador não foi encontrado.", exception.getMessage());

        verify(compradorRepository, never()).delete(any());
    }
}
