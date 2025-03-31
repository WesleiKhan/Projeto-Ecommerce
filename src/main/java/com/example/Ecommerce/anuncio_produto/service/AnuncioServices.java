package com.example.Ecommerce.anuncio_produto.service;

import java.io.IOException;
import java.util.Optional;

import com.example.Ecommerce.anuncio_produto.exceptions.AnuncioNotFound;
import com.example.Ecommerce.user.exceptions.UserNotAutorization;
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
import com.example.Ecommerce.user.vendedor.entity.Vendedor;
import com.example.Ecommerce.user.vendedor.repositorie.VendedorRepository;

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
                    "de vendedores");
        }
    }


    public void updateAnuncio(String id, AnuncioEntryDTO data) throws IOException{

        User user = userServices.getLoggedInUser();

        Vendedor vendeUser =
                vendedorRepository.findByNome(user)
                        .orElseThrow(() -> new UserNotFound("Vendedor com o Nome de "
                                + user.getPrimeiro_nome() + " Não foi Encontrado."));

        Anuncio newAnuncio =
                anuncioRepository.findById(id).orElseThrow(AnuncioNotFound::new);

        if(vendeUser.equals(newAnuncio.getVendedor())) {

            /*A Logica Para ve se os valores atribuidos a os metodos sets
            são null ou blank esta dentros dos metodos sets da entidade,
            se os valores que estão sendo atribuido forem null ou um string
            vazia o valor não sera atualizado no banco de dados.*/

            newAnuncio.setTitulo(data.getTitulo());
            newAnuncio.setDescricao(data.getDescricao());
            newAnuncio.setValor(data.getValor());
            newAnuncio.setQuantidade_produto(data.getQuantidade());
            newAnuncio.setAltura(data.getAltura());
            newAnuncio.setLargura(data.getLargura());
            newAnuncio.setComprimento(data.getComprimento());
            newAnuncio.setPeso(data.getPeso());

            if(data.getImagem() != null && !data.getImagem().isEmpty()) {
                String newImagem =
                        fileUploadImpl.updloadFile(data.getImagem());
                newAnuncio.setImagem(newImagem);
            }

            anuncioRepository.save(newAnuncio);

        } else {
            throw new UserNotAutorization();
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
