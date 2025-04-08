package com.example.Ecommerce.user.vendedor.service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;

import com.example.Ecommerce.user.service.UserServices;
import com.example.Ecommerce.utils.service.sendGrid.interfaces.SendGridAdpted;
import com.example.Ecommerce.utils.service.stripe.interfaces.StripeAccountLinkAdpted;
import com.example.Ecommerce.utils.service.stripe.interfaces.StripeConnectAdpted;
import com.example.Ecommerce.utils.service.stripe.interfaces.StripeExcludeAccountAdpted;
import org.springframework.stereotype.Service;

import com.example.Ecommerce.user.entity.User;
import com.example.Ecommerce.user.exceptions.UserAlreadyExists;
import com.example.Ecommerce.user.exceptions.UserNotFound;
import com.example.Ecommerce.user.vendedor.entity.Vendedor;
import com.example.Ecommerce.user.vendedor.repositorie.VendedorRepository;
import com.stripe.exception.StripeException;

@Service
public class VendedorServices {

    private final VendedorRepository vendedorRepository;

    private final UserServices userServices;

    private final StripeConnectAdpted stripeConnectAdpted;

    private final StripeAccountLinkAdpted stripeAccountLinkAdpted;

    private final StripeExcludeAccountAdpted stripeExcludeAccountAdpted;

    private final SendGridAdpted sendGridAdpted;


    public VendedorServices(VendedorRepository vendedorRepository,
                            UserServices userServices,
                            StripeConnectAdpted stripeConnectAdpted,
                            StripeAccountLinkAdpted stripeAccountLinkAdpted,
                            StripeExcludeAccountAdpted stripeExcludeAccountAdpted,
                            SendGridAdpted sendGridAdpted) {

        this.vendedorRepository = vendedorRepository;
        this.userServices = userServices;
        this.stripeConnectAdpted = stripeConnectAdpted;
        this.stripeAccountLinkAdpted = stripeAccountLinkAdpted;
        this.stripeExcludeAccountAdpted = stripeExcludeAccountAdpted;
        this.sendGridAdpted = sendGridAdpted;

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

        String id_stripe = stripeConnectAdpted.criarContaVendedorStripe(
                infoVendedor.getEmail(), infoVendedor.getPrimeiro_nome(),
                infoVendedor.getSobrenome(), dia, mes, ano, data.getCpf(),
                data.getNumero_telefone(), data.getConta(), data.getAgencia(),
                data.getCodigo_banco(), data.getEndereco());

        String urlCadastro = stripeAccountLinkAdpted.criarLinkDeOnboading(id_stripe);

        Vendedor newVendedor = new Vendedor(data.getCpf(), data.getCnpj(),
                data.getNumero_telefone(), data.getEndereco()
                );

        newVendedor.setNome(infoVendedor);

        newVendedor.setId_account_stripe(id_stripe);

        sendGridAdpted.sendEmail(urlCadastro, infoVendedor.getEmail());
           
        vendedorRepository.save(newVendedor);

    }

    public void updateVendedor(VendedorEntryEditDTO data) {

        User user = userServices.getLoggedInUser();

        Optional<Vendedor> vendeOptional =
                vendedorRepository.findByNome(user);

        if(vendeOptional.isPresent()) {

            Vendedor newVendedor = vendeOptional.get();

            /*A Logica Para ve se os valores atribuidos a os metodos sets
            são null ou blank esta dentros dos metodos sets da entidade,
            se os valores que estão sendo atribuido forem null ou um string
            vazia o valor não sera atualizado no banco de dados.*/

            newVendedor.setNumero_telefone(data.getNumeroTelefone());
            newVendedor.setEndereco(data.getEndereco());

            vendedorRepository.save(newVendedor);

        }else {
            throw new UserNotFound("Vendedor não foi encontrado.");
        }
    }

    public String deleteVendedor() throws  Exception {

        User user = userServices.getLoggedInUser();

        Vendedor vendedor = vendedorRepository.findByNome(user)
                .orElseThrow(() -> new UserNotFound("Vendedor não foi encontrado."));

        String response = stripeExcludeAccountAdpted.deleteAccountStripe(vendedor
                    .getId_account_stripe());

        user.setCadastro_vendedor(null);

        vendedorRepository.delete(vendedor);

        return response;

    }

}









