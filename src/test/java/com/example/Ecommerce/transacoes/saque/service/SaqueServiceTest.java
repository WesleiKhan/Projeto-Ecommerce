package com.example.Ecommerce.transacoes.saque.service;

import com.example.Ecommerce.client.service.stripe.contract.StripeTransfer;
import com.example.Ecommerce.transacoes.saque.entity.Saque;
import com.example.Ecommerce.transacoes.saque.execeptions.SaqueInvalidoException;
import com.example.Ecommerce.transacoes.saque.repositorie.SaqueRepository;
import com.example.Ecommerce.transacoes.pagamento.entity.Pagamento;
import com.example.Ecommerce.transacoes.pagamento.repositorie.PagamentoRepository;
import com.example.Ecommerce.user.entity.User;
import com.example.Ecommerce.user.exceptions.UserNotFound;
import com.example.Ecommerce.user.service.UserService;
import com.example.Ecommerce.user.vendedor.entity.Vendedor;
import com.example.Ecommerce.user.vendedor.repositorie.VendedorRepository;
import com.stripe.exception.StripeException;
import com.stripe.model.Transfer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
public class SaqueServiceTest {

    @Mock
    SaqueRepository saqueRepository;

    @Mock
    UserService userService;

    @Mock
    PagamentoRepository pagamentoRepository;

    @Mock
    VendedorRepository vendedorRepository;

    @Mock
    StripeTransfer stripeTransfer;

    @InjectMocks
    SaqueService saqueService;

    private User loggedInUser;
    private Vendedor vendedor;
    private Saque saque;
    private Pagamento pagamento;
    private long valorEmCendtavos;

    @BeforeEach
    void setUp() {

        this.loggedInUser = new User("caio", "ribeiro", "caioR",
                "caio@gmail.com", LocalDate.of(1988,5, 25),
                "caio123");
        loggedInUser.setId("1");

        Saque saque1 = new Saque();
        saque1.setId("213");

        List<Saque> saques = new ArrayList<>();
        saques.add(saque1);

        this.vendedor = new Vendedor();
        vendedor.setId("3");
        vendedor.setNome(loggedInUser);
        vendedor.setId_account_stripe("id_Account");
        vendedor.setSaques(saques);

        this.pagamento = new Pagamento();
        pagamento.setValor_total(new BigDecimal(1000));
        pagamento.setId("321");
        pagamento.setId_charge_stripe("id_charge");

        this.saque = new Saque();
        saque.setId("123");
        saque.setSacador(vendedor);
        saque.setTransacao(pagamento);

        this.valorEmCendtavos = 90000;
    }

    @Test
    void sacar_wihtValidInput() throws StripeException {

        when(userService.getLoggedInUser()).thenReturn(loggedInUser);

        when(vendedorRepository.findByNome(loggedInUser)).thenReturn(Optional.of(vendedor));

        when(pagamentoRepository.findById("321")).thenReturn(Optional.of(pagamento));

        when(saqueRepository.findByTransacao(pagamento)).thenReturn(Optional.empty());

        when(stripeTransfer.createTransfer(anyString(), anyString(),
                anyLong())).thenReturn(mock(Transfer.class));

        saqueService.sacar("321");

        verify(userService).getLoggedInUser();

        verify(vendedorRepository).findByNome(loggedInUser);

        verify(pagamentoRepository).findById("321");

        verify(saqueRepository).findByTransacao(pagamento);

        verify(stripeTransfer).createTransfer(vendedor.getId_account_stripe(),
                pagamento.getId_charge_stripe(), valorEmCendtavos);

        verify(saqueRepository).save(any(Saque.class));
    }

    @Test
    void sacar_whenUserInvalid_thorwsUserNotFound() throws StripeException {

        when(userService.getLoggedInUser()).thenReturn(loggedInUser);

        when(vendedorRepository.findByNome(loggedInUser)).thenReturn(Optional.empty());

        UserNotFound exception = assertThrows(UserNotFound.class,
                () -> saqueService.sacar("321"));

        assertEquals("Vendedor não foi encontrador.", exception.getMessage());

        verify(stripeTransfer, never()).createTransfer(anyString(),
                anyString(), anyLong());

        verify(saqueRepository, never()).save(any());

    }

    @Test
    void sacar_whenInvalidInput_thorwsRuntimeException() throws StripeException {

        when(userService.getLoggedInUser()).thenReturn(loggedInUser);

        when(vendedorRepository.findByNome(loggedInUser)).thenReturn(Optional.of(vendedor));

        when(pagamentoRepository.findById("321")).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> saqueService.sacar("321"));

        assertEquals("Trasação não encontrada", exception.getMessage());

        verify(stripeTransfer, never()).createTransfer(anyString(),
                anyString(), anyLong());

        verify(saqueRepository, never()).save(any());
    }

    @Test
    void sacar_whenSaqueExist_thorwsSaqueInvalidoException() throws StripeException{

        when(userService.getLoggedInUser()).thenReturn(loggedInUser);

        when(vendedorRepository.findByNome(loggedInUser)).thenReturn(Optional.of(vendedor));

        when(pagamentoRepository.findById("321")).thenReturn(Optional.of(pagamento));

        when(saqueRepository.findByTransacao(pagamento)).thenReturn(Optional.of(saque));

        SaqueInvalidoException exception = assertThrows(SaqueInvalidoException.class,
                () -> saqueService.sacar("321"));

        assertEquals("O Usuario ja Realizou o Saque dessa Transação!", exception.getMessage());

        verify(stripeTransfer, never()).createTransfer(anyString(),
                anyString(), anyLong());

        verify(saqueRepository, never()).save(any());
    }

    @Test
    void seeSaques_wihtValidInput() {

        when(userService.getLoggedInUser()).thenReturn(loggedInUser);

        when(vendedorRepository.findByNome(loggedInUser)).thenReturn(Optional.of(vendedor));

        List<Saque> response = saqueService.seeSaques();

        assertEquals("213", response.get(0).getId());

        verify(userService).getLoggedInUser();

        verify(vendedorRepository).findByNome(loggedInUser);

    }

    @Test
    void seeSaques_whenUserNotExist_thorwsUserNotFound() {

        when(userService.getLoggedInUser()).thenReturn(loggedInUser);

        when(vendedorRepository.findByNome(loggedInUser)).thenReturn(Optional.empty());

        UserNotFound exception = assertThrows(UserNotFound.class,
                () -> saqueService.seeSaques());

        assertEquals("Vendedor não foi encontrado.", exception.getMessage());
    }
}
