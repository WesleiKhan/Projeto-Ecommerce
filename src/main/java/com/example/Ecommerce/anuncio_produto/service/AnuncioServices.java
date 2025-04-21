package com.example.Ecommerce.anuncio_produto.service;

import java.io.IOException;

import com.example.Ecommerce.anuncio_produto.exceptions.AnuncioNotFound;
import com.example.Ecommerce.user.exceptions.UserNotAutorization;
import com.example.Ecommerce.user.service.UserServices;
import com.example.Ecommerce.utils.service.cloudinary.interfaces.FileUpload;
import com.example.Ecommerce.utils.service.melhorEnvio.interfaces.Frete;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.Ecommerce.anuncio_produto.entity.Anuncio;
import com.example.Ecommerce.anuncio_produto.repositorie.AnuncioRepository;
import com.example.Ecommerce.user.entity.User;
import com.example.Ecommerce.user.exceptions.UserNotFound;
import com.example.Ecommerce.utils.exceptions.FreteException;
import com.example.Ecommerce.utils.service.DTOs.CepEntryDTO;
import com.example.Ecommerce.utils.service.DTOs.FreteEntryDTO;
import com.example.Ecommerce.user.vendedor.entity.Vendedor;
import com.example.Ecommerce.user.vendedor.repositorie.VendedorRepository;

@Service
public class AnuncioServices {

    private final AnuncioRepository anuncioRepository;

    private final UserServices userServices;

    private final VendedorRepository vendedorRepository;

    private final FileUpload fileUpload;

    private final Frete frete;

    public AnuncioServices(AnuncioRepository anuncioRepository,
                           UserServices userServices,
                           VendedorRepository vendedorRepository,
                           FileUpload fileUpload,
                           Frete frete) {

        this.anuncioRepository = anuncioRepository;
        this.userServices = userServices;
        this.vendedorRepository = vendedorRepository;
        this.fileUpload = fileUpload;
        this.frete = frete;
    }

    public void createAnuncio(AnuncioEntryDTO data) throws IOException{

        User user = userServices.getLoggedInUser();

        Vendedor vendedor = vendedorRepository.findByNome(user)
                .orElseThrow(() -> new UserNotFound("Usuario não foi encontrado no cadastro " +
                        "de vendedores"));

        String imagem = fileUpload.updloadFile(data.getImagem()) ;

        Anuncio newAnuncio = new Anuncio(data.getTitulo(), data.getDescricao(),
                imagem, data.getValor(), data.getQuantidade(),
                data.getAltura(), data.getLargura(), data.getComprimento(),
                data.getPeso());

        newAnuncio.setVendedor(vendedor);

        anuncioRepository.save(newAnuncio);


    }


    public void updateAnuncio(String id, AnuncioEntryDTO data) throws IOException{

        User user = userServices.getLoggedInUser();

        Vendedor vendeUser =
                vendedorRepository.findByNome(user)
                        .orElseThrow(() -> new UserNotFound("Vendedor Não foi Encontrado."));

        Anuncio newAnuncio =
                anuncioRepository.findById(id).orElseThrow(AnuncioNotFound::new);

        if(!newAnuncio.vendedorEquals(vendeUser)) {
            throw new UserNotAutorization();
        }

        if(data.getImagem() != null && !data.getImagem().isEmpty()) {
            String newImagem =
                    fileUpload.updloadFile(data.getImagem());

            newAnuncio.atualizarDados(data, newImagem);
        }

        anuncioRepository.save(newAnuncio);
    }
    
    public Page<Anuncio> getAnuncios(int page) {

        Pageable pageable = PageRequest.of(page, 40);
        return anuncioRepository.findAll(pageable);
    }


    public String verFrete(String id, CepEntryDTO cep_destino) {

        Anuncio anuncio = anuncioRepository.findById(id)
                .orElseThrow(FreteException::new);


        Vendedor vendedor = anuncio.getVendedor();

        return frete.calcularFrete(new FreteEntryDTO(vendedor
                        .getEndereco()
                        .getCep(),
                        anuncio.getAltura(),
                        anuncio.getLargura(),
                        anuncio.getComprimento(),
                        anuncio.getPeso()),
                        cep_destino);


    }
}
