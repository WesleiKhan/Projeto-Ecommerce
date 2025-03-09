package com.example.Ecommerce.anuncio_produto.service;

import java.io.IOException;
import java.util.Optional;

import com.example.Ecommerce.user.service.UserServices;
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
import com.example.Ecommerce.utils.service.cloudinary.FileUploadImpl;
import com.example.Ecommerce.utils.service.melhorEnvio.FreteServices;
import com.example.Ecommerce.vendedor.entity.Vendedor;
import com.example.Ecommerce.vendedor.repositorie.VendedorRepository;

@Service
public class AnuncioServices {

    private final AnuncioRepository anuncioRepository;

    private final UserServices userServices;

    private final VendedorRepository vendedorRepository;

    private final FileUploadImpl fileUploadImpl;

    private final FreteServices freteServices;

    public AnuncioServices(AnuncioRepository anuncioRepository,
                           UserServices userServices,
                           VendedorRepository vendedorRepository,
                           FileUploadImpl fileUploadImpl,
                           FreteServices freteServices) {

        this.anuncioRepository = anuncioRepository;
        this.userServices = userServices;
        this.vendedorRepository = vendedorRepository;
        this.fileUploadImpl = fileUploadImpl;
        this.freteServices = freteServices;
    }

    public void createAnuncio(AnuncioEntryDTO data) throws IOException{

        User user = userServices.getLoggedInUser();

        Optional<Vendedor> userV = vendedorRepository.findByNome(user);

        if (userV.isPresent()) {

            Vendedor vendedor = userV.get();

            String imagem = fileUploadImpl.updloadFile(data.getImagem()) ;

            Anuncio newAnuncio = new Anuncio(data.getTitulo(), data.getDescricao(),
                    imagem, data.getValor(), data.getQuantidade(),
                    data.getAltura(), data.getLargura(), data.getComprimento(),
                    data.getPeso());

            newAnuncio.setVendedor(vendedor);

            anuncioRepository.save(newAnuncio);

        } else {
            throw new UserNotFound("Usuario não foi encontrado no cadastro " +
                    "de vendedores, " + " por favor siga ate a aba de " +
                    "cadastro de vendedores para se cadastrar!");
        }
    }


    public void updateAnuncio(String id, AnuncioEntryDTO data) throws IOException{

        User user = userServices.getLoggedInUser();

        Vendedor vendeUser =
                vendedorRepository.findByNome(user)
                        .orElseThrow(() -> new UserNotFound("Vendedor com o Nome de "
                                + user.getPrimeiro_nome() + " Não foi Encontrado."));

        Anuncio newAnuncio = anuncioRepository.findById(id).orElseThrow();

        if(vendeUser.equals(newAnuncio.getVendedor())) {

            if(data.getTitulo() != null && !data.getTitulo().trim().isEmpty()) {

                newAnuncio.setTitulo(data.getTitulo());
            }
            if(data.getDescricao() != null && !data.getDescricao().trim().isEmpty()) {

                newAnuncio.setDescricao(data.getDescricao());
            }
            if(data.getImagem() != null && !data.getImagem().isEmpty()) {

                String newImagem =
                        fileUploadImpl.updloadFile(data.getImagem());

                newAnuncio.setImagem(newImagem);
            }
            if(data.getValor() != null) {

                newAnuncio.setValor(data.getValor());
            }
            if(data.getQuantidade() != null) {

                newAnuncio.setQuantidade_produto(data.getQuantidade());
            }
            if(data.getAltura() != null) {

                newAnuncio.setAltura(data.getAltura());
            }
            if(data.getLargura() != null) {

                newAnuncio.setLargura(data.getLargura());
            }
            if(data.getComprimento() != null) {

                newAnuncio.setComprimento(data.getComprimento());
            }
            if(data.getPeso() != null) {

                newAnuncio.setPeso(data.getPeso());
            }

            anuncioRepository.save(newAnuncio);

        } else {
            throw new RuntimeException("Voce não tem permissão para realizar" +
                    " essa ação");
        }
    }
    
    public Page<Anuncio> getAnuncios(int page) {

        Pageable pageable = PageRequest.of(page, 40);
        return anuncioRepository.findAll(pageable);
    }


    public String verFrete(String id, CepEntryDTO cep_destino) {

        Optional<Anuncio> anuOptional = anuncioRepository.findById(id);

        if (anuOptional.isPresent()) {

            Anuncio anuncio = anuOptional.get();

            Vendedor vendedor = anuncio.getVendedor();

            return freteServices.calcularFrete(new FreteEntryDTO(vendedor.getCep(),
                    anuncio.getAltura(),
                    anuncio.getLargura(),
                    anuncio.getComprimento(),
                    anuncio.getPeso()),
                    cep_destino);

        } else {
            throw new FreteException();
        }

    }
}
