package com.example.Ecommerce.user.service;

import com.example.Ecommerce.auth.service.CustomUserDetails;
import com.example.Ecommerce.user.entity.User;
import com.example.Ecommerce.user.exceptions.UserAlreadyExists;
import com.example.Ecommerce.user.exceptions.UserNotAutorization;
import com.example.Ecommerce.user.exceptions.UserNotFound;
import com.example.Ecommerce.user.repositorie.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserService userService;

    @Test
    void createUser_withValidInput() {

        UserEntryDTO entry = new UserEntryDTO();
        entry.setPrimeiro_nome("weslei");
        entry.setSobrenome("Alves Dos Santos");
        entry.setUsername("weslei");
        entry.setEmail("weslei@gmail.com");
        entry.setData_nascimento(LocalDate.of(2000,9,21));
        entry.setPassword("wes2024");

        when(userRepository.findByEmail(entry.getEmail()))
                .thenReturn(Optional.empty());

        userService.createUser(entry);

        verify(userRepository).save(any(User.class));
    }

    @Test
    void createUser_withInvalidInput_throwsUserAlreadyExistsException() {

        UserEntryDTO entry = new UserEntryDTO();
        entry.setPrimeiro_nome("weslei");
        entry.setSobrenome("Alves Dos Santos");
        entry.setUsername("weslei");
        entry.setEmail("weslei@gmail.com");
        entry.setData_nascimento(LocalDate.of(2000,9,21));
        entry.setPassword("wes2024");

        when(userRepository.findByEmail(entry.getEmail()))
                .thenReturn(Optional.of(new User()));

        assertThrows(UserAlreadyExists.class,
                () -> userService.createUser(entry));
    }

    @Test
    void updateUser_withValidInput() {

        UserEntryEditDTO entry = new UserEntryEditDTO();
        entry.setPrimeiroNome("weslei");
        entry.setSobrenome("Alves Dos Santos");
        entry.setUsername("weslei");
        entry.setDataNascimento(LocalDate.of(2000,9,21));
        entry.setPassword("wes2024");

        User loggendUser = new User("weslei", "alves dos santos",
                "weslei", "weslei@gmail.com",
                LocalDate.of(2000,9,21), "weslei123");
        loggendUser.setId("123");

        CustomUserDetails userDetails = mock(CustomUserDetails.class);
        when(userDetails.getId()).thenReturn("123");

        Authentication auth = mock(Authentication.class);
        when(auth.getPrincipal()).thenReturn(userDetails);

        SecurityContext context = mock(SecurityContext.class);
        when(context.getAuthentication()).thenReturn(auth);

        SecurityContextHolder.setContext(context);

        when(userRepository.findById("123")).thenReturn(Optional.of(loggendUser));

        userService.updateUser(entry);

        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void updateUser_UserNotAuthorization_throwsUserNotAutorizationException() {

        UserEntryEditDTO entry = new UserEntryEditDTO();
        entry.setPrimeiroNome("weslei");
        entry.setSobrenome("Alves Dos Santos");
        entry.setUsername("weslei");
        entry.setDataNascimento(LocalDate.of(2000,9,21));
        entry.setPassword("wes2024");


        CustomUserDetails userDetails = mock(CustomUserDetails.class);
        when(userDetails.getId()).thenReturn("123");

        Authentication auth = mock(Authentication.class);
        when(auth.getPrincipal()).thenReturn(userDetails);

        SecurityContext context = mock(SecurityContext.class);
        when(context.getAuthentication()).thenReturn(auth);

        SecurityContextHolder.setContext(context);

        when(userRepository.findById("123")).thenReturn(Optional.empty());

        assertThrows(UserNotAutorization.class,
                () -> userService.updateUser(entry));
    }

    @Test
    void deleteUser_withValidInput() {

        User user = new User("weslei", "alves dos santos",
                "weslei", "weslei@gmail.com",
                LocalDate.of(2000,9,21), "weslei123");
        user.setId("123");

        when(userRepository.findById("123")).thenReturn(Optional.of(user));

        userService.deleteUser("123");

        verify(userRepository, times(1)).delete(user);

    }

    @Test
    void deleteUser_withIvalidInput_throwsUserNotFoundException() {

        when(userRepository.findById("123")).thenReturn(Optional.empty());

        assertThrows(UserNotFound.class, () -> userService.deleteUser("123"));
    }

    @Test
    void getLoggedInUser_withValidInput() {

        User user = new User("weslei", "alves dos santos",
                "weslei", "weslei@gmail.com",
                LocalDate.of(2000,9,21), "weslei123");
        user.setId("123");

        CustomUserDetails userDetails = mock(CustomUserDetails.class);
        when(userDetails.getId()).thenReturn("123");

        Authentication auth = mock(Authentication.class);
        when(auth.getPrincipal()).thenReturn(userDetails);

        SecurityContext context = mock(SecurityContext.class);
        when(context.getAuthentication()).thenReturn(auth);

        SecurityContextHolder.setContext(context);

        when(userRepository.findById("123")).thenReturn(Optional.of(user));

        User result = userService.getLoggedInUser();

        assertEquals("123", result.getId());
        assertEquals("weslei", result.getUsername());
        assertEquals("weslei@gmail.com", result.getEmail());

    }

    @Test
    void getLoggendInUser_throwsUserNotAuthorization() {

        CustomUserDetails userDetails = mock(CustomUserDetails.class);
        when(userDetails.getId()).thenReturn("123");

        Authentication auth = mock(Authentication.class);
        when(auth.getPrincipal()).thenReturn(userDetails);

        SecurityContext context = mock(SecurityContext.class);
        when(context.getAuthentication()).thenReturn(auth);

        SecurityContextHolder.setContext(context);

        when(userRepository.findById("123")).thenReturn(Optional.empty());

        assertThrows(UserNotAutorization.class,
                () -> userService.getLoggedInUser());
    }
}
