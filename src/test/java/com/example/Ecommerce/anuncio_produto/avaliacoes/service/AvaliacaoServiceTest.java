package com.example.Ecommerce.anuncio_produto.avaliacoes.service;

import com.example.Ecommerce.anuncio_produto.avaliacoes.DTOs.AvaliacaoEntryDTO;
import com.example.Ecommerce.anuncio_produto.avaliacoes.DTOs.AvaliacaoResponseDTO;
import com.example.Ecommerce.anuncio_produto.avaliacoes.DTOs.ResponseSQlAvaliacoes;
import com.example.Ecommerce.anuncio_produto.avaliacoes.entity.Avaliacao;
import com.example.Ecommerce.anuncio_produto.avaliacoes.repositorie.AvaliacaoRepository;
import com.example.Ecommerce.anuncio_produto.entity.Anuncio;
import com.example.Ecommerce.anuncio_produto.exceptions.AnuncioNotFound;
import com.example.Ecommerce.anuncio_produto.repositorie.AnuncioRepository;
import com.example.Ecommerce.transacoes.pagamento.entity.Pagamento;
import com.example.Ecommerce.user.comprador.entity.Comprador;
import com.example.Ecommerce.user.comprador.repositorie.CompradorRepository;
import com.example.Ecommerce.user.entity.User;
import com.example.Ecommerce.user.exceptions.UserNotAutorization;
import com.example.Ecommerce.user.exceptions.UserNotFound;
import com.example.Ecommerce.user.service.UserService;
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
public class AvaliacaoServiceTest {

    @Mock
    AvaliacaoRepository avaliacaoRepository;

    @Mock
    UserService userService;

    @Mock
    CompradorRepository compradorRepository;

    @Mock
    AnuncioRepository anuncioRepository;

    @InjectMocks
    AvaliacaoService avaliacaoService;

    private User loggedInUser;
    private Comprador comprador;
    private Anuncio anuncio;
    private AvaliacaoEntryDTO entry;
    private List<Avaliacao> avaliacoes;
    private ResponseSQlAvaliacoes responseSql;
    private AvaliacaoResponseDTO response;

    @BeforeEach
    void setUp() {

        this.loggedInUser = new User("caio", "ribeiro", "caioR",
                "caio@gmail.com", LocalDate.of(1988,5, 25),
                "caio123");

        this.comprador = new Comprador();
        comprador.setNome(loggedInUser);

        this.anuncio = new Anuncio();
        anuncio.setId("123");

        this.entry = new AvaliacaoEntryDTO();
        entry.setNota(new BigDecimal(5));
        entry.setComentario("comentario exemplo");

        this.avaliacoes = new ArrayList<>();

        this.responseSql = new ResponseSQlAvaliacoes(new BigDecimal(4),
                new BigDecimal(2), new BigDecimal(1), new BigDecimal(3));

        this.response = new AvaliacaoResponseDTO(responseSql, avaliacoes);

    }

    @Test
    void register_withValidInput() {

        when(userService.getLoggedInUser()).thenReturn(loggedInUser);

        Comprador compradorMock = mock(Comprador.class);
        compradorMock.setId("123");
        compradorMock.setNome(loggedInUser);

        Pagamento pagamentoMock = mock(Pagamento.class);
        pagamentoMock.setProduto(anuncio);
        pagamentoMock.setComprador(compradorMock);

        when(compradorRepository.findByNome(loggedInUser))
                .thenReturn(Optional.of(compradorMock));

        when(anuncioRepository.findById("123")).thenReturn(Optional.of(anuncio));

        when(compradorMock.transacaoExiste(anuncio)).thenReturn(true);

        Avaliacao avaliacaoMock = mock(Avaliacao.class);
        avaliacaoMock.setProduto(anuncio);
        avaliacaoMock.setAutor(compradorMock);

        avaliacoes.add(avaliacaoMock);

        when(avaliacaoRepository.findByProduto(anuncio))
                .thenReturn(avaliacoes);

        boolean avaliacaoEncontrada = avaliacoes.stream()
                .anyMatch(avaliacao -> avaliacao.authorEquals(compradorMock));

        when(avaliacaoEncontrada).thenReturn(false);

        avaliacaoService.register("123", entry);

        verify(userService).getLoggedInUser();

        verify(compradorRepository).findByNome(loggedInUser);

        verify(anuncioRepository).findById("123");

        verify(avaliacaoRepository).save(any(Avaliacao.class));
    }

    @Test
    void register_whenCompradorNotExists_thworsUserNotFound() {

        when(userService.getLoggedInUser()).thenReturn(loggedInUser);

        when(compradorRepository.findByNome(loggedInUser)).thenReturn(Optional.empty());

        UserNotFound exception = assertThrows(UserNotFound.class,
                () -> avaliacaoService.register("123", entry));

        assertEquals("Comprador não foi encontrado", exception.getMessage());

        verify(avaliacaoRepository, never()).save(any());
    }

    @Test
    void register_whenAnuncioNotExists_thorwsAnuncioNotFound() {

        when(userService.getLoggedInUser()).thenReturn(loggedInUser);

        when(compradorRepository.findByNome(loggedInUser)).thenReturn(Optional.of(comprador));

        when(anuncioRepository.findById("123")).thenReturn(Optional.empty());

        AnuncioNotFound exception = assertThrows(AnuncioNotFound.class,
                () -> avaliacaoService.register("123", entry));

        assertEquals("Anuncio não foi encontrado.", exception.getMessage());

        verify(avaliacaoRepository, never()).save(any());
    }

    @Test
    void register_whenTransacaoNotExists_thorwsAnuncioNotFound() {

        when(userService.getLoggedInUser()).thenReturn(loggedInUser);

        Comprador compradorMock = mock(Comprador.class);
        compradorMock.setId("123");
        compradorMock.setNome(loggedInUser);

        Pagamento pagamentoMock = mock(Pagamento.class);
        pagamentoMock.setProduto(anuncio);
        pagamentoMock.setComprador(compradorMock);

        when(compradorRepository.findByNome(loggedInUser))
                .thenReturn(Optional.of(compradorMock));

        when(anuncioRepository.findById("123")).thenReturn(Optional.of(anuncio));

        when(compradorMock.transacaoExiste(anuncio)).thenReturn(false);

        AnuncioNotFound exception = assertThrows(AnuncioNotFound.class,
                () -> avaliacaoService.register("123", entry));

        assertEquals("Você não pode avaliar um anuncio " +
                "de um produto que você não fez a compra.", exception.getMessage());

        verify(avaliacaoRepository, never()).save(any());
    }

    @Test
    void register_whenAvaliaçãoExist_thorwsUserNotAuthorization() {

        when(userService.getLoggedInUser()).thenReturn(loggedInUser);

        Comprador compradorMock = mock(Comprador.class);
        compradorMock.setId("123");
        compradorMock.setNome(loggedInUser);

        Pagamento pagamentoMock = mock(Pagamento.class);
        pagamentoMock.setProduto(anuncio);
        pagamentoMock.setComprador(compradorMock);

        when(compradorRepository.findByNome(loggedInUser))
                .thenReturn(Optional.of(compradorMock));

        when(anuncioRepository.findById("123")).thenReturn(Optional.of(anuncio));

        when(compradorMock.transacaoExiste(anuncio)).thenReturn(true);

        Avaliacao avaliacaoMock = mock(Avaliacao.class);
        avaliacaoMock.setProduto(anuncio);
        avaliacaoMock.setAutor(compradorMock);

        avaliacoes.add(avaliacaoMock);

        when(avaliacaoRepository.findByProduto(anuncio))
                .thenReturn(avaliacoes);

        boolean avaliacaoEncontrada = avaliacoes.stream()
                .anyMatch(avaliacao -> avaliacao.authorEquals(compradorMock));

        when(avaliacaoEncontrada).thenReturn(true);

        UserNotAutorization exception = assertThrows(UserNotAutorization.class,
                () -> avaliacaoService.register("123", entry));

        assertEquals("Você ja avaliou esse anuncio.", exception.getMessage());

        verify(avaliacaoRepository, never()).save(any());
    }

    @Test
    void seeAvaliacoes_withValidInput() {

        when(anuncioRepository.findById("123")).thenReturn(Optional.of(anuncio));

        when(avaliacaoRepository.findByAvaliacoes("123"))
                .thenReturn(Optional.of(responseSql));

        AvaliacaoResponseDTO responseDTO = avaliacaoService.seeAvaliacoes(
                "123");

        assertEquals(response.notas().media(), responseDTO.notas().media());

        assertEquals(response.notas().positivas(), responseDTO.notas().positivas());

        verify(anuncioRepository).findById("123");

        verify(avaliacaoRepository).findByAvaliacoes("123");

    }

    @Test
    void seeAvaliacoes_whenInvalidInputFromAnuncio_thorwsAnuncioNotFound() {

        when(anuncioRepository.findById("123")).thenReturn(Optional.empty());

        AnuncioNotFound exception = assertThrows(AnuncioNotFound.class,
                () -> avaliacaoService.seeAvaliacoes("123"));

        assertEquals("Anuncio não foi encontrado.", exception.getMessage());

    }

    @Test
    void seeAvaliacoes_whenInvalidInputAvaliacao_thorwsRuntimeException() {

        when(anuncioRepository.findById("123")).thenReturn(Optional.of(anuncio));

        when(avaliacaoRepository.findByAvaliacoes("123"))
                .thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> avaliacaoService.seeAvaliacoes("123"));

        assertEquals("erro ao obter avaliações do produto.",
                exception.getMessage());

        verify(anuncioRepository).findById("123");
    }
}
