package com.example.Ecommerce.utils.service.stripe.interfaces;

import com.example.Ecommerce.user.objectValue.Endereco;
import com.stripe.exception.StripeException;

public interface StripeConnectAdpted {
    String criarContaVendedorStripe(String email, String nome,
                                    String sobrenome, long dia,
                                    long mes, long ano, String cpf,
                                    String telefone, String conta,
                                    String agencia, String code_bank,
                                    Endereco endereco)
            throws StripeException;
}
