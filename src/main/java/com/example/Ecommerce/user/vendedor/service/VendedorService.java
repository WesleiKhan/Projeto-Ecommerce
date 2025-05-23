package com.example.Ecommerce.user.vendedor.service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;

import com.example.Ecommerce.user.service.UserService;
import com.example.Ecommerce.client.service.sendGrid.contract.EmailSender;
import com.example.Ecommerce.client.service.stripe.contract.StripeAccountLink;
import com.example.Ecommerce.client.service.stripe.contract.StripeConnect;
import com.example.Ecommerce.client.service.stripe.contract.StripeExcludeAccount;
import org.springframework.stereotype.Service;

import com.example.Ecommerce.user.entity.User;
import com.example.Ecommerce.user.exceptions.UserAlreadyExists;
import com.example.Ecommerce.user.exceptions.UserNotFound;
import com.example.Ecommerce.user.vendedor.entity.Vendedor;
import com.example.Ecommerce.user.vendedor.repositorie.VendedorRepository;
import com.stripe.exception.StripeException;

@Service
public class VendedorService {

    private final VendedorRepository vendedorRepository;

    private final UserService userService;

    private final StripeConnect stripeConnect;

    private final StripeAccountLink stripeAccountLink;

    private final StripeExcludeAccount stripeExcludeAccount;

    private final EmailSender emailSender;


    public VendedorService(VendedorRepository vendedorRepository,
                           UserService userService,
                           StripeConnect stripeConnect,
                           StripeAccountLink stripeAccountLink,
                           StripeExcludeAccount stripeExcludeAccount,
                           EmailSender emailSender) {

        this.vendedorRepository = vendedorRepository;
        this.userService = userService;
        this.stripeConnect = stripeConnect;
        this.stripeAccountLink = stripeAccountLink;
        this.stripeExcludeAccount = stripeExcludeAccount;
        this.emailSender = emailSender;

    }

    public void createVendedor(VendedorEntryDTO data) throws StripeException,
            IOException{

        User infoVendedor = userService.getLoggedInUser();

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

        User user = userService.getLoggedInUser();

        Vendedor newVendedor = vendedorRepository.findByNome(user)
                .orElseThrow(() -> new UserNotFound("Vendedor não foi encontrado."));

        newVendedor.atualizarDados(data);

        vendedorRepository.save(newVendedor);

    }

    public String deleteVendedor() throws  Exception {

        User user = userService.getLoggedInUser();

        Vendedor vendedor = vendedorRepository.findByNome(user)
                .orElseThrow(() -> new UserNotFound("Vendedor não foi encontrado."));

        String response = stripeExcludeAccount.deleteAccountStripe(vendedor
                    .getId_account_stripe());

        user.setCadastro_vendedor(null);

        vendedorRepository.delete(vendedor);

        return response;

    }

}









