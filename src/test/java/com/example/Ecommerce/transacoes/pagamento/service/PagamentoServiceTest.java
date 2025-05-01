package com.example.Ecommerce.transacoes.pagamento.service;

import com.example.Ecommerce.anuncio_produto.entity.Anuncio;
import com.example.Ecommerce.anuncio_produto.exceptions.AnuncioNotFound;
import com.example.Ecommerce.anuncio_produto.repositorie.AnuncioRepository;
import com.example.Ecommerce.client.service.stripe.contract.StripePayment;
import com.example.Ecommerce.transacoes.pagamento.entity.Pagamento;
import com.example.Ecommerce.transacoes.pagamento.repositorie.PagamentoRepository;
import com.example.Ecommerce.user.comprador.entity.Comprador;
import com.example.Ecommerce.user.comprador.repositorie.CompradorRepository;
import com.example.Ecommerce.user.entity.User;
import com.example.Ecommerce.user.exceptions.UserNotFound;
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

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class PagamentoServiceTest {

    @Mock
    PagamentoRepository pagamentoRepository;

    @Mock
    UserService userService;

    @Mock
    AnuncioRepository anuncioRepository;

    @Mock
    VendedorRepository vendedorRepository;

    @Mock
    CompradorRepository compradorRepository;

    @Mock
    StripePayment stripePayment;

    @InjectMocks
    PagamentoService pagamentoService;

    private User loggedInUser;
    private Anuncio anuncio;
    private Comprador comprador;
    private Vendedor vendedor;
    private PagamentoEntryDTO entry;
    private StringBuilder charge_id;
    private long valoTotalCentavos;
    private Pagamento pagamento;

    @BeforeEach
    void setUp() {

        this.loggedInUser = new User("caio", "ribeiro", "caioR",
                "caio@gmail.com", LocalDate.of(1988,5, 25),
                "caio123");
        loggedInUser.setId("1");

        this.vendedor = new Vendedor();
        vendedor.setId("3");
        vendedor.setNome(loggedInUser);
        vendedor.setId_account_stripe("id_Account");

        this.comprador = new Comprador();
        comprador.setId("1");
        vendedor.setNome(loggedInUser);

        this.anuncio = new Anuncio();
        anuncio.setId("321");
        anuncio.setValor(new BigDecimal(1000));
        anuncio.setVendedor(vendedor);

        this.entry = new PagamentoEntryDTO();
        entry.setQuantidade(1);
        entry.setToken("card_token");

        this.charge_id = new StringBuilder();
        charge_id.append("charge_payment_id");

        this.valoTotalCentavos = 100000;

        this.pagamento = new Pagamento();
        pagamento.setId("123");
        pagamento.setProduto(anuncio);


    }

    @Test
    void makePayment_withValidInput() throws StripeException {

        when(userService.getLoggedInUser()).thenReturn(loggedInUser);

        when(compradorRepository.findByNome(loggedInUser)).thenReturn(Optional.of(comprador));

        when(anuncioRepository.findById("321")).thenReturn(Optional.of(anuncio));

        when(stripePayment.createPaymentIntent(anyString(),
                anyLong() )).thenReturn(charge_id.toString());

        pagamentoService.makePayment("321", entry);

        verify(userService).getLoggedInUser();

        verify(compradorRepository).findByNome(loggedInUser);

        verify(anuncioRepository).findById("321");

        verify(stripePayment).createPaymentIntent(entry.getToken(),
                valoTotalCentavos);

        verify(pagamentoRepository).save(any(Pagamento.class));
    }

    @Test
    void makePayment_whenCompradorNotExist_thorwsUserNotFound() throws StripeException {

        when(userService.getLoggedInUser()).thenReturn(loggedInUser);

        when(compradorRepository.findByNome(loggedInUser)).thenReturn(Optional.empty());

        UserNotFound exception = assertThrows(UserNotFound.class,
                () -> pagamentoService.makePayment("321", entry));

        assertEquals("Usuario não foi encontrado no Cadatro de" +
                " Compradores.", exception.getMessage());

        verify(stripePayment, never()).createPaymentIntent(anyString(),
                anyLong());

        verify(pagamentoRepository, never()).save(any());
    }

    @Test
    void makePayment_whenAnuncioNotExist_thorwsAnuncioNotFound() throws StripeException {

        when(userService.getLoggedInUser()).thenReturn(loggedInUser);

        when(compradorRepository.findByNome(loggedInUser)).thenReturn(Optional.of(comprador));

        when(anuncioRepository.findById("321")).thenReturn(Optional.empty());

        AnuncioNotFound exception = assertThrows(AnuncioNotFound.class,
                () -> pagamentoService.makePayment("321", entry));

        assertEquals("Anuncio não foi encontrado.", exception.getMessage());

        verify(stripePayment, never()).createPaymentIntent(anyString(),
                anyLong());

        verify(pagamentoRepository, never()).save(any());
    }

    @Test
    void seeTransacoes_withValidInput() {

        when(userService.getLoggedInUser()).thenReturn(loggedInUser);

        when(vendedorRepository.findByNome(loggedInUser)).thenReturn(Optional.of(vendedor));

        when(pagamentoRepository.findByProdutoVendedor(vendedor)).thenReturn(List.of(pagamento));

        List<Pagamento> response = pagamentoService.seeTransacao();

        assertEquals("123", response.get(0).getId());
    }

    @Test
    void seeTransacoes_whenVendedorNotExist_thorwsUserNotFound() {

        when(userService.getLoggedInUser()).thenReturn(loggedInUser);

        when(vendedorRepository.findByNome(loggedInUser)).thenReturn(Optional.empty());

        UserNotFound exception = assertThrows(UserNotFound.class,
                () -> pagamentoService.seeTransacao());

        assertEquals("Usuario não foi encontrado!", exception.getMessage());
    }

}
