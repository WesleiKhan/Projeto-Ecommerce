package com.example.Ecommerce.user.vendedor.service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;

import com.example.Ecommerce.user.service.UserServices;
import com.example.Ecommerce.utils.service.sendGrid.interfaces.EmailSender;
import com.example.Ecommerce.utils.service.stripe.interfaces.StripeAccountLink;
import com.example.Ecommerce.utils.service.stripe.interfaces.StripeConnect;
import com.example.Ecommerce.utils.service.stripe.interfaces.StripeExcludeAccount;
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

    private final StripeConnect stripeConnect;

    private final StripeAccountLink stripeAccountLink;

    private final StripeExcludeAccount stripeExcludeAccount;

    private final EmailSender emailSender;


    public VendedorServices(VendedorRepository vendedorRepository,
                            UserServices userServices,
                            StripeConnect stripeConnect,
                            StripeAccountLink stripeAccountLink,
                            StripeExcludeAccount stripeExcludeAccount,
                            EmailSender emailSender) {

        this.vendedorRepository = vendedorRepository;
        this.userServices = userServices;
        this.stripeConnect = stripeConnect;
        this.stripeAccountLink = stripeAccountLink;
        this.stripeExcludeAccount = stripeExcludeAccount;
        this.emailSender = emailSender;

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

        String id_stripe = stripeConnect.criarContaVendedorStripe(
                infoVendedor.getEmail(), infoVendedor.getPrimeiro_nome(),
                infoVendedor.getSobrenome(), dia, mes, ano, data.getCpf(),
                data.getNumero_telefone(), data.getConta(), data.getAgencia(),
                data.getCodigo_banco(), data.getEndereco());

        String urlCadastro = stripeAccountLink.criarLinkDeOnboading(id_stripe);

        Vendedor newVendedor = new Vendedor(data.getCpf(), data.getCnpj(),
                data.getNumero_telefone(), data.getEndereco()
                );

        newVendedor.setNome(infoVendedor);

        newVendedor.setId_account_stripe(id_stripe);

        emailSender.sendEmail(urlCadastro, infoVendedor.getEmail());
           
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

        String response = stripeExcludeAccount.deleteAccountStripe(vendedor
                    .getId_account_stripe());

        user.setCadastro_vendedor(null);

        vendedorRepository.delete(vendedor);

        return response;

    }

}









