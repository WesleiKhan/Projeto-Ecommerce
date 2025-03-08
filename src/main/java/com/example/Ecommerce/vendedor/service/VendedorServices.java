package com.example.Ecommerce.vendedor.service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.Ecommerce.auth.service.CustomUserDetails;
import com.example.Ecommerce.user.entity.User;
import com.example.Ecommerce.user.exceptions.UserAlreadyExists;
import com.example.Ecommerce.user.exceptions.UserNotFound;
import com.example.Ecommerce.user.repositorie.UserRepository;
import com.example.Ecommerce.utils.service.sendGrid.SendGridServices;
import com.example.Ecommerce.utils.service.stripe.StripeAccountLinkServices;
import com.example.Ecommerce.utils.service.stripe.StripeConnectServices;
import com.example.Ecommerce.vendedor.entity.Vendedor;
import com.example.Ecommerce.vendedor.repositorie.VendedorRepository;
import com.stripe.exception.StripeException;

@Service
public class VendedorServices {

    @Autowired
    private VendedorRepository vendedorRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StripeConnectServices stripeConnectServices;

    @Autowired
    private StripeAccountLinkServices stripeAccountLinkServices;

    @Autowired
    private SendGridServices sendGridServices;

    public Vendedor createVendedor(VendedorEntryDTO data) throws StripeException,
            IOException{

        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();

        String userId = userDetails.getId();

        Optional<User> user = userRepository.findById(userId);

        if (user.isPresent()) {

            User infoVendedor = user.get();

            if (vendedorRepository.findByNome(infoVendedor).isPresent()) {
                throw new UserAlreadyExists("Usuario ja e Cadastrado como Vendedor!");}

            LocalDate dataNascimento = infoVendedor.getData_nascimento();

            long dia = (long) dataNascimento.getDayOfMonth();
            long mes = (long) dataNascimento.getMonthValue();
            long ano = (long) dataNascimento.getYear();

            String id_stripe = stripeConnectServices.criarContaVendedorStripe(
                    infoVendedor.getEmail(), infoVendedor.getPrimeiro_nome(),
                    infoVendedor.getSobrenome(), dia, mes, ano, data.getCpf(),
                    data.getNumero_telefone(), data.getConta(), data.getAgencia(),
                    data.getCodigo_banco(), data.getRua(), data.getNumero(),
                    data.getCidade(), data.getEstado(), data.getCep());

            String urlCadastro = stripeAccountLinkServices.criarLinkDeOnboading(id_stripe);

            Vendedor newVendedor = new Vendedor(data.getCpf(), data.getCnpj(),
                    data.getNumero_telefone(), data.getRua(), data.getNumero(),
                    data.getCidade(), data.getEstado(), data.getCep(),
                    data.getAgencia(), data.getConta(), data.getCodigo_banco());

            newVendedor.setNome(infoVendedor);

            newVendedor.setId_account_stripe(id_stripe);

            sendGridServices.sendEmail(urlCadastro, infoVendedor.getEmail());
           
            return vendedorRepository.save(newVendedor);

        } else {
            throw new UserNotFound();
        }
    }

    public Vendedor updateVendedor(VendedorEntryEditDTO data) {

        CustomUserDetails userDetails =
                (CustomUserDetails) SecurityContextHolder.getContext().
                        getAuthentication().getPrincipal();

        String userId = userDetails.getId();

        User user =
                userRepository.findById(userId).orElseThrow(() -> new UserNotFound());

        Optional<Vendedor> vendeOptional =
                vendedorRepository.findByNome(user);

        if(vendeOptional.isPresent()) {

            Vendedor newVendedor = vendeOptional.get();

            if(data.getNumeroTelefone() != null && !data.getNumeroTelefone()
                    .trim().isEmpty()) {

                newVendedor.setNumero_telefone(data.getNumeroTelefone());
            }
            if(data.getRua() != null && !data.getRua().trim().isEmpty()) {

                newVendedor.setRua(data.getRua());
            }
            if(data.getNumero() != null && !data.getNumero().trim().isEmpty()) {

                newVendedor.setNumero(data.getNumero());
            }
            if(data.getCidade() != null && !data.getCidade().trim().isEmpty()) {

                newVendedor.setCidade(data.getCidade());
            }
            if(data.getEstado() != null && !data.getEstado().trim().isEmpty()) {

                newVendedor.setEstado(data.getEstado());
            }
            if(data.getCep() != null && !data.getCep().trim().isEmpty()) {

                newVendedor.setCep(data.getCep());
            }

            return vendedorRepository.save(newVendedor);

        }else {
            throw new UserNotFound("Vendedor não foi encontrado!");
        }

    }
}
