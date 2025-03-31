package com.example.Ecommerce.utils.service.stripe.interfaces;

import com.stripe.exception.StripeException;

public interface StripeConnectAdpted {
    String criarContaVendedorStripe(String email, String nome,
                                    String sobrenome, long dia,
                                    long mes, long ano, String cpf,
                                    String telefone, String conta,
                                    String agencia, String code_bank,
                                    String rua, String numero, String cidade,
                                    String estado, String cep)
            throws StripeException;
}
