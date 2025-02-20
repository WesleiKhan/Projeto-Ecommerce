package com.example.Ecommerce.utils.service.stripe;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Account;
import com.stripe.param.AccountCreateParams;

@Service
public class StripeConnectServices {

    @Value("${stripe.api.key}")
    private String apiKeyStripe;
    
    public String criarContaVendedorStripe(String email, String nome, String sobrenome, long dia, long mes, long ano, String cpf, String telefone, String conta, String agencia, String code_bank, String rua, String numero, String cidade, String estado, String cep) throws StripeException {

        Stripe.apiKey = apiKeyStripe;

        try {
            AccountCreateParams params = AccountCreateParams.builder()
                .setType(AccountCreateParams.Type.EXPRESS)
                .setCountry("BR")
                .setEmail(email)
                .setBusinessType(AccountCreateParams.BusinessType.INDIVIDUAL) // Ou COMPANY para empresas
                .setIndividual(
                    AccountCreateParams.Individual.builder()
                        .setFirstName(nome)
                        .setLastName(sobrenome)
                        .setIdNumber(cpf) // CPF do vendedor
                        .setPhone("+55" + telefone)
                        .setDob(
                            AccountCreateParams.Individual.Dob.builder()
                                .setDay(dia)       
                                .setMonth(mes)      
                                .setYear(ano)    
                                .build()
                        )
                        .putExtraParam("address[line1]", rua + ", " + numero)
                        .putExtraParam("address[city]", cidade)
                        .putExtraParam("address[state]", estado)
                        .putExtraParam("address[postal_code]", cep)
                        .putExtraParam("address[country]", "BR")
                        .build()
                )
                .setCapabilities(
                    AccountCreateParams.Capabilities.builder()
                        .setTransfers(AccountCreateParams.Capabilities.Transfers.builder().setRequested(true).build()) // Permite transferências
                        .setCardPayments(AccountCreateParams.Capabilities.CardPayments.builder().setRequested(true).build()) // Permite pagamentos com cartão
                        .build()
                )
                
            .build();

            Account account = Account.create(params); 

            adicionarContaBancaria(account.getId(), code_bank, agencia, conta, nome);

            return account.getId();

        } catch (StripeException e) {
            
            System.out.println("O Erro e " + e);
            e.printStackTrace();
            throw e;
        }
        
    }

    private void adicionarContaBancaria(String accountId, String code_bank, String agencia, String conta, String nome) throws StripeException {

        try {

            String routingNumber = code_bank + agencia; 

            Map<String, Object> bankAccountParams = new HashMap<>();
            Map<String, Object> bankAccountDetails = new HashMap<>();
        
            // Detalhes da conta bancária dentro de "bank_account"
            bankAccountDetails.put("country", "BR");
            bankAccountDetails.put("currency", "brl");
            bankAccountDetails.put("account_holder_name", nome);
            bankAccountDetails.put("account_holder_type", "individual");
            bankAccountDetails.put("routing_number", routingNumber);
            bankAccountDetails.put("account_number", conta);

            // Adiciona o mapa de detalhes sob a chave "bank_account"
            bankAccountParams.put("bank_account", bankAccountDetails);

            Account account = Account.retrieve(accountId);
            account.getExternalAccounts().create(bankAccountParams);

        } catch (StripeException e) {
            
            System.out.println("O Erro ao adicionar conta bancaria ao vendedor " + e);
            e.printStackTrace();
            throw e;
        }
        
    }
}
