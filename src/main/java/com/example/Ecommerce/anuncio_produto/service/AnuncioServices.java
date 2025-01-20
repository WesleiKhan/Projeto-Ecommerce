package com.example.Ecommerce.anuncio_produto.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.Ecommerce.anuncio_produto.entity.Anuncio;
import com.example.Ecommerce.anuncio_produto.repositorie.AnuncioRepository;
import com.example.Ecommerce.auth.service.CustomUserDetails;
import com.example.Ecommerce.user.entity.User;
import com.example.Ecommerce.user.exceptions.UserNotFound;
import com.example.Ecommerce.user.repositorie.UserRepository;
import com.example.Ecommerce.utils.exceptions.FreteException;
import com.example.Ecommerce.utils.service.CepEntryDTO;
import com.example.Ecommerce.utils.service.FileUploadImpl;
import com.example.Ecommerce.utils.service.FreteEntryDTO;
import com.example.Ecommerce.utils.service.FreteServices;
import com.example.Ecommerce.vendedor.entity.Vendedor;
import com.example.Ecommerce.vendedor.repositorie.VendedorRepository;

@Service
public class AnuncioServices {

    @Autowired
    private AnuncioRepository anuncioRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VendedorRepository vendedorRepository;

    @Autowired
    private FileUploadImpl fileUploadImpl;

    @Autowired
    private FreteServices freteServices;

    public Anuncio createAnuncio(AnuncioEntryDTO data) throws IOException{

        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String userId = userDetails.getId();

        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFound());

        Optional<Vendedor> userV = vendedorRepository.findByNome(user);

        if (userV.isPresent()) {

            Vendedor vendedor = userV.get();

            String imagem = fileUploadImpl.updloadFile(data.getImagem()) ;

            Anuncio newAnuncio = new Anuncio(data.getTitulo(), data.getDescricao(), imagem, data.getValor(), data.getQuantidade(), data.getAltura(), data.getLargura(), data.getComprimento(), data.getPeso());

            newAnuncio.setVendedor(vendedor);

            return anuncioRepository.save(newAnuncio);

        } else {
            throw new UserNotFound("Usuario não foi encontrado no cadastro de vendedores, por favor siga ate a aba de cadastro de vendedores para se cadastrar!");
        }
    }
    
    public List<Anuncio> getAnuncios() {

        return anuncioRepository.findAll();
    }

    public String verFrete(String id, CepEntryDTO cep_destino) {

        Optional<Anuncio> anuOptional = anuncioRepository.findById(id);

        if (anuOptional.isPresent()) {

            Anuncio anuncio = anuOptional.get();

            Vendedor vendedor = anuncio.getVendedor();

            String freteResult = freteServices.calcularFrete(new FreteEntryDTO(vendedor.getCep(), anuncio.getAltura(), anuncio.getLargura(), anuncio.getComprimento(), anuncio.getPeso()), cep_destino);

            return freteResult;

        } else {
            throw new FreteException();
        }

    }
}
