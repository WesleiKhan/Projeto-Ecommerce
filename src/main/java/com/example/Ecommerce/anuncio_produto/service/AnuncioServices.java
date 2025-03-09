package com.example.Ecommerce.anuncio_produto.service;

import java.io.IOException;
import java.util.Optional;

import com.example.Ecommerce.user.service.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.Ecommerce.anuncio_produto.entity.Anuncio;
import com.example.Ecommerce.anuncio_produto.repositorie.AnuncioRepository;
import com.example.Ecommerce.user.entity.User;
import com.example.Ecommerce.user.exceptions.UserNotFound;
import com.example.Ecommerce.user.repositorie.UserRepository;
import com.example.Ecommerce.utils.exceptions.FreteException;
import com.example.Ecommerce.utils.service.DTOs.CepEntryDTO;
import com.example.Ecommerce.utils.service.DTOs.FreteEntryDTO;
import com.example.Ecommerce.utils.service.cloudinary.FileUploadImpl;
import com.example.Ecommerce.utils.service.melhorEnvio.FreteServices;
import com.example.Ecommerce.vendedor.entity.Vendedor;
import com.example.Ecommerce.vendedor.repositorie.VendedorRepository;

@Service
public class AnuncioServices {

    @Autowired
    private AnuncioRepository anuncioRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserServices userServices;

    @Autowired
    private VendedorRepository vendedorRepository;

    @Autowired
    private FileUploadImpl fileUploadImpl;

    @Autowired
    private FreteServices freteServices;

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
