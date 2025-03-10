package com.example.Ecommerce.vendedor.service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;

import com.example.Ecommerce.user.service.UserServices;
import org.springframework.stereotype.Service;

import com.example.Ecommerce.user.entity.User;
import com.example.Ecommerce.user.exceptions.UserAlreadyExists;
import com.example.Ecommerce.user.exceptions.UserNotFound;
import com.example.Ecommerce.utils.service.sendGrid.SendGridServices;
import com.example.Ecommerce.utils.service.stripe.StripeAccountLinkServices;
import com.example.Ecommerce.utils.service.stripe.StripeConnectServices;
import com.example.Ecommerce.vendedor.entity.Vendedor;
import com.example.Ecommerce.vendedor.repositorie.VendedorRepository;
import com.stripe.exception.StripeException;

@Service
public class VendedorServices {

    private final VendedorRepository vendedorRepository;

    private final UserServices userServices;

    private final StripeConnectServices stripeConnectServices;

    private final StripeAccountLinkServices stripeAccountLinkServices;

    private final SendGridServices sendGridServices;


    public VendedorServices(VendedorRepository vendedorRepository,
                            UserServices userServices,
                            StripeConnectServices stripeConnectServices,
                            StripeAccountLinkServices stripeAccountLinkServices,
                            SendGridServices sendGridServices) {

        this.vendedorRepository = vendedorRepository;
        this.userServices = userServices;
        this.stripeConnectServices = stripeConnectServices;
        this.stripeAccountLinkServices = stripeAccountLinkServices;
        this.sendGridServices = sendGridServices;

    }

    public void createVendedor(VendedorEntryDTO data) throws StripeException,
            IOException{

        User infoVendedor = userServices.getLoggedInUser();

        if (vendedorRepository.findByNome(infoVendedor).isPresent()) {
            throw new UserAlreadyExists("Usuario ja e Cadastrado como Vendedor!");}

        LocalDate dataNascimento = infoVendedor.getData_nascimento();

        long dia =  dataNascimento.getDayOfMonth();
        long mes =  dataNascimento.getMonthValue();
        long ano =  dataNascimento.getYear();

        String id_stripe = stripeConnectServices.criarContaVendedorStripe(
                infoVendedor.getEmail(), infoVendedor.getPrimeiro_nome(),
                infoVendedor.getSobrenome(), dia, mes, ano, data.getCpf(),
                data.getNumero_telefone(), data.getConta(), data.getAgencia(),
                data.getCodigo_banco(), data.getRua(), data.getNumero(),
                data.getCidade(), data.getEstado(), data.getCep());

        String urlCadastro = stripeAccountLinkServices.criarLinkDeOnboading(id_stripe);

        Vendedor newVendedor = new Vendedor(data.getCpf(), data.getCnpj(),
                data.getNumero_telefone(), data.getRua(), data.getNumero(),
                data.getCidade(), data.getEstado(), data.getCep()
                );

        newVendedor.setNome(infoVendedor);

        newVendedor.setId_account_stripe(id_stripe);

        sendGridServices.sendEmail(urlCadastro, infoVendedor.getEmail());
           
        vendedorRepository.save(newVendedor);

    }

    public void updateVendedor(VendedorEntryEditDTO data) {

        User user = userServices.getLoggedInUser();

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

            vendedorRepository.save(newVendedor);

        }else {
            throw new UserNotFound("Vendedor não foi encontrado!");
        }
    }

}









