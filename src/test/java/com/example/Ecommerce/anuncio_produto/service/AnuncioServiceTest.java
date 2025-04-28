package com.example.Ecommerce.anuncio_produto.service;

import com.example.Ecommerce.anuncio_produto.entity.Anuncio;
import com.example.Ecommerce.anuncio_produto.exceptions.AnuncioNotFound;
import com.example.Ecommerce.anuncio_produto.repositorie.AnuncioRepository;
import com.example.Ecommerce.client.exceptions.FreteException;
import com.example.Ecommerce.client.service.DTOs.CepEntryDTO;
import com.example.Ecommerce.client.service.DTOs.FreteEntryDTO;
import com.example.Ecommerce.client.service.cloudinary.contract.FileUpload;
import com.example.Ecommerce.client.service.melhorEnvio.contract.Frete;
import com.example.Ecommerce.user.entity.User;
import com.example.Ecommerce.user.exceptions.UserNotAutorization;
import com.example.Ecommerce.user.exceptions.UserNotFound;
import com.example.Ecommerce.user.objectValue.Endereco;
import com.example.Ecommerce.user.service.UserService;
import com.example.Ecommerce.user.vendedor.entity.Vendedor;
import com.example.Ecommerce.user.vendedor.repositorie.VendedorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class AnuncioServiceTest {

    @Mock
    AnuncioRepository anuncioRepository;

    @Mock
    UserService userService;

    @Mock
    VendedorRepository vendedorRepository;

    @Mock
    FileUpload fileUpload;

    @Mock
    Frete frete;

    @InjectMocks
    AnuncioService anuncioService;

    private User loggedInUser;
    private AnuncioEntryDTO entry;
    private MultipartFile imagem;
    private Anuncio anuncio;
    private Vendedor vendedor;
    private String url;
    private Endereco endereco;
    private FreteEntryDTO freteEntry;
    private CepEntryDTO cepDestino;

    @BeforeEach
    void setUp() {

        this.loggedInUser = new User("caio", "ribeiro", "caioR",
                "caio@gmail.com", LocalDate.of(1988,5, 25),
                "caio123");

        this.imagem = new MockMultipartFile(
                "arquivo",
                "teste.txt",
                "text/plain",
                "conteudo do arquivo".getBytes()
        );

        this.entry = new AnuncioEntryDTO();
        entry.setTitulo("exemplo titulo");
        entry.setDescricao("exemplo descrição");
        entry.setImagem(imagem);
        entry.setValor(new BigDecimal(1000));
        entry.setQuantidade(52);
        entry.setAltura(12.3);
        entry.setLargura(45.2);
        entry.setComprimento(32.1);
        entry.setPeso(5.0);

        this.endereco = new Endereco();
        endereco.setCep("21354000");

        this.vendedor = new Vendedor();
        vendedor.setId("321");
        vendedor.setNome(loggedInUser);
        vendedor.setEndereco(endereco);

        this.anuncio = new Anuncio(entry.getTitulo(), entry.getDescricao(),
                "imagem", entry.getValor(), entry.getQuantidade(), entry.getAltura(),
                entry.getLargura(), entry.getComprimento(), entry.getPeso());
        anuncio.setId("123");
        anuncio.setVendedor(vendedor);


        this.url = "http://res.cloudinary.com/df8jxlrfk/image/upload/v1737228963/" +
                "f5c8edc3-f4ae-4aa2-9085-010f1068df63.png";

        this.freteEntry = new FreteEntryDTO(vendedor.getEndereco().getCep(),
                anuncio.getAltura(), anuncio.getLargura(),
                anuncio.getComprimento(), anuncio.getPeso());

        this.cepDestino = new CepEntryDTO();
        cepDestino.setCep("21354000");

    }

    @Test
    void createAnuncio_withValidInput() throws IOException {

        when(userService.getLoggedInUser()).thenReturn(loggedInUser);

        when(vendedorRepository.findByNome(loggedInUser)).thenReturn(Optional.of(vendedor));

        when(fileUpload.updloadFile(entry.getImagem()))
                .thenReturn(url);

        anuncioService.createAnuncio(entry);

        verify(userService).getLoggedInUser();

        verify(vendedorRepository).findByNome(loggedInUser);

        verify(fileUpload).updloadFile(entry.getImagem());

        verify(anuncioRepository).save(any(Anuncio.class));
    }

    @Test
    void createAnuncio_whenInvalidUser_thorwsUserNotFound() throws IOException {

        when(userService.getLoggedInUser()).thenReturn(loggedInUser);

        when(vendedorRepository.findByNome(loggedInUser)).thenReturn(Optional.empty());

        UserNotFound exception = assertThrows(UserNotFound.class,
                () -> anuncioService.createAnuncio(entry));

        assertEquals("Usuario não foi encontrado no cadastro " +
                "de vendedores", exception.getMessage());

        verify(anuncioRepository, never()).save(any());
    }

    @Test
    void updateAnuncio_withValidInput() throws IOException {

        when(userService.getLoggedInUser()).thenReturn(loggedInUser);

        when(vendedorRepository.findByNome(loggedInUser)).thenReturn(Optional.of(vendedor));

        when(anuncioRepository.findById("123")).thenReturn(Optional.of(anuncio));

        when(fileUpload.updloadFile(entry.getImagem())).thenReturn(url);

        anuncioService.updateAnuncio("123", entry);

        verify(userService).getLoggedInUser();

        verify(vendedorRepository).findByNome(loggedInUser);

        verify(anuncioRepository).findById("123");

        verify(fileUpload).updloadFile(entry.getImagem());

        verify(anuncioRepository).save(any(Anuncio.class));
    }

    @Test
    void updateAnuncio_whenInvalidUser_thorwsUserNotFound() {

        when(userService.getLoggedInUser()).thenReturn(loggedInUser);

        when(vendedorRepository.findByNome(loggedInUser)).thenReturn(Optional.empty());

        UserNotFound exception = assertThrows(UserNotFound.class,
                () -> anuncioService.updateAnuncio( "123",entry));

        assertEquals("Vendedor Não foi Encontrado.", exception.getMessage());

        verify(anuncioRepository, never()).save(any());
    }

    @Test
    void updateAnuncio_withInvalidInputId_thorwsAnuncioNotFound() {

        when(userService.getLoggedInUser()).thenReturn(loggedInUser);

        when(vendedorRepository.findByNome(loggedInUser)).thenReturn(Optional.of(vendedor));

        when(anuncioRepository.findById("123")).thenReturn(Optional.empty());

        AnuncioNotFound exception = assertThrows(AnuncioNotFound.class,
                () -> anuncioService.updateAnuncio("123", entry));

        assertEquals("Anuncio não foi encontrado.", exception.getMessage());

        verify(anuncioRepository, never()).save(any());
    }

    @Test
    void updateAnuncio_whenUserUnauthorized_thorwsUserNotAutorization() {

        when(userService.getLoggedInUser()).thenReturn(loggedInUser);

        when(vendedorRepository.findByNome(loggedInUser)).thenReturn(Optional.of(vendedor));

        Anuncio anuncioMock = mock(Anuncio.class);
        anuncioMock.setId("123");
        when(anuncioRepository.findById("123")).thenReturn(Optional.of(anuncioMock));

        when(anuncioMock.vendedorEquals(vendedor)).thenReturn(false);

        UserNotAutorization exception = assertThrows(UserNotAutorization.class,
                () -> anuncioService.updateAnuncio("123", entry));

        assertEquals("Usuario não Autorizado.", exception.getMessage());

        verify(anuncioRepository, never()).save(any());
    }

    @Test
    void verFrete_withValidInput() {

        when(anuncioRepository.findById("123")).thenReturn(Optional.of(anuncio));

        when(frete.calcularFrete(freteEntry, cepDestino)).thenReturn("Tempo " +
                "de Entrega: 12 dias, Valor do Frete: R$  10 ");

        String response = frete.calcularFrete(freteEntry, cepDestino);

        anuncioService.verFrete("123", cepDestino);

        assertEquals("Tempo de Entrega: 12 dias, Valor do Frete: R$  10 ",
                response);

    }

    @Test
    void verFrete_withInvalidInput_thorwsFreteException() {

        when(anuncioRepository.findById("123")).thenReturn(Optional.empty());

        FreteException exception = assertThrows(FreteException.class,
                () -> anuncioService.verFrete("123", cepDestino));

        assertEquals("Error ao Efetuar o calculo de frete",
                exception.getMessage());
    }

}
