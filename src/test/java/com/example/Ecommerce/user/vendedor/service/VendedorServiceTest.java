package com.example.Ecommerce.user.vendedor.service;

import com.example.Ecommerce.client.service.sendGrid.contract.EmailSender;
import com.example.Ecommerce.client.service.stripe.contract.StripeAccountLink;
import com.example.Ecommerce.client.service.stripe.contract.StripeConnect;
import com.example.Ecommerce.client.service.stripe.contract.StripeExcludeAccount;
import com.example.Ecommerce.user.entity.User;
import com.example.Ecommerce.user.exceptions.UserAlreadyExists;
import com.example.Ecommerce.user.exceptions.UserNotFound;
import com.example.Ecommerce.user.objectValue.Endereco;
import com.example.Ecommerce.user.service.UserService;
import com.example.Ecommerce.user.vendedor.entity.Vendedor;
import com.example.Ecommerce.user.vendedor.repositorie.VendedorRepository;
import com.stripe.exception.StripeException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class VendedorServiceTest {

    @Mock
    VendedorRepository vendedorRepository;

    @Mock
    UserService userService;

    @Mock
    StripeConnect stripeConnect;

    @Mock
    StripeAccountLink stripeAccountLink;

    @Mock
    EmailSender emailSender;

    @Mock
    StripeExcludeAccount stripeExcludeAccount;

    @InjectMocks
    VendedorService vendedorService;

    private User loggedInUser;
    private VendedorEntryDTO entry;
    private Endereco endereco;
    private VendedorEntryEditDTO entryEdit;

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

        this.entry = new VendedorEntryDTO();
        entry.setCpf("12345678998");
        entry.setCnpj("");
        entry.setNumero_telefone("89542612387");
        entry.setEndereco(endereco);
        entry.setConta("383880");
        entry.setAgencia("8101");
        entry.setCodigo_banco("3832181");

        this.entryEdit = new VendedorEntryEditDTO();
        entryEdit.setNumeroTelefone("89542612387");
        entryEdit.setEndereco(endereco);
    }

    @Test
    void createVendedor_withValidInput() throws StripeException, IOException {

        when(userService.getLoggedInUser()).thenReturn(loggedInUser);

        when(vendedorRepository.findByNome(loggedInUser))
                .thenReturn(Optional.empty());

        when(stripeConnect.criarContaVendedorStripe(
                anyString(), anyString(), anyString(), anyLong(), anyLong(), anyLong(),
                anyString(), anyString(), anyString(), anyString(),
               anyString(), any(Endereco.class)
        )).thenReturn("stripe_account_id");

        when(stripeAccountLink.criarLinkDeOnboading("stripe_account_id"))
                .thenReturn("http://stripe-onboarding-url");

        vendedorService.createVendedor(entry);

        verify(userService).getLoggedInUser();

        verify(vendedorRepository).findByNome(loggedInUser);

        verify(stripeConnect).criarContaVendedorStripe(
                eq(loggedInUser.getEmail()),
                eq(loggedInUser.getPrimeiro_nome()),
                eq(loggedInUser.getSobrenome()),
                eq((long) loggedInUser.getData_nascimento().getDayOfMonth()),
                eq((long) loggedInUser.getData_nascimento().getMonthValue()),
                eq((long) loggedInUser.getData_nascimento().getYear()),
                eq(entry.getCpf()),
                eq(entry.getNumero_telefone()),
                eq((entry.getConta())),
                eq(entry.getAgencia()),
                eq(entry.getCodigo_banco()),
                eq(entry.getEndereco())
        );

        verify(stripeAccountLink).criarLinkDeOnboading("stripe_account_id");

        verify(emailSender).sendEmail("http://stripe-onboarding-url",
                loggedInUser.getEmail());

        verify(vendedorRepository).save(any(Vendedor.class));

    }

    @Test
    void createVendedor_whenUserIsAlreadyASeller_throwsUserAlreadyExistsException()
            throws StripeException, IOException{

        when(userService.getLoggedInUser()).thenReturn(loggedInUser);

        Vendedor vendedor = new Vendedor();
        vendedor.setNome(loggedInUser);

        when(vendedorRepository.findByNome(loggedInUser))
                .thenReturn(Optional.of(vendedor));


        UserAlreadyExists exceptio = assertThrows(UserAlreadyExists.class,
                () -> vendedorService.createVendedor(entry));

        assertEquals("Usuario ja e Cadastrado como Vendedor!",
                exceptio.getMessage());

        verify(stripeConnect, never()).criarContaVendedorStripe(any(), any(),
                any(), anyLong(), anyLong(), anyLong(), any(), any(), any(),
                any(), any(), any());

        verify(emailSender, never()).sendEmail(any(), any());

        verify(vendedorRepository, never()).save(any());

    }

    @Test
    void updateVendedor_withValidInput() {

        when(userService.getLoggedInUser()).thenReturn(loggedInUser);

        Vendedor vendedor = new Vendedor();
        vendedor.setEndereco(endereco);
        vendedor.setNome(loggedInUser);

        when(vendedorRepository.findByNome(loggedInUser))
                .thenReturn(Optional.of(vendedor));

        vendedorService.updateVendedor(entryEdit);

        verify(userService).getLoggedInUser();

        verify(vendedorRepository).findByNome(loggedInUser);

        verify(vendedorRepository, times(1))
                .save(any(Vendedor.class));

    }

    @Test
    void updateVendedor_whenInvalidUser_throwsUserNotFound() {

        when(userService.getLoggedInUser()).thenReturn(loggedInUser);

        when(vendedorRepository.findByNome(loggedInUser))
                .thenReturn(Optional.empty());

        UserNotFound exception =
                assertThrows(UserNotFound.class,
                        () -> vendedorService.updateVendedor(entryEdit));

        assertEquals("Vendedor não foi encontrado.", exception.getMessage());

        verify(vendedorRepository, never())
                .save(any());
    }

    @Test
    void deleteVendedor_withValidUser() throws Exception {

        when(userService.getLoggedInUser()).thenReturn(loggedInUser);

        Vendedor vendedor = new Vendedor();
        vendedor.setNome(loggedInUser);
        vendedor.setId_account_stripe("stripe_account_id");

        when(vendedorRepository.findByNome(loggedInUser))
                .thenReturn(Optional.of(vendedor));

        when(stripeExcludeAccount.deleteAccountStripe(vendedor.getId_account_stripe()))
                .thenReturn("200 : conta deletada");

        String response = vendedorService.deleteVendedor();

        assertEquals("200 : conta deletada", response);

        verify(userService).getLoggedInUser();

        verify(stripeExcludeAccount).deleteAccountStripe(vendedor.getId_account_stripe());

        verify(vendedorRepository).findByNome(loggedInUser);

        verify(vendedorRepository, times(1)).delete(vendedor);

    }

    @Test
    void deleteUser_withInvalidUser_throwsUserNotFound() throws Exception {

        when(userService.getLoggedInUser()).thenReturn(loggedInUser);

        when(vendedorRepository.findByNome(loggedInUser))
                .thenReturn(Optional.empty());

        UserNotFound exception =
                assertThrows(UserNotFound.class, () ->vendedorService.deleteVendedor());

        assertEquals("Vendedor não foi encontrado.", exception.getMessage());

        verify(vendedorRepository, never()).delete(any());

    }
}
