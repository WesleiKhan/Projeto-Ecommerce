package com.example.Ecommerce.client.service.melhorEnvio.contract;

import com.example.Ecommerce.client.service.DTOs.CepEntryDTO;
import com.example.Ecommerce.client.service.DTOs.FreteEntryDTO;

public interface Frete {

    String calcularFrete(FreteEntryDTO data, CepEntryDTO cep_destino);
}
