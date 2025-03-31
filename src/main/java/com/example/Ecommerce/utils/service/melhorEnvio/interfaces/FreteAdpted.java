package com.example.Ecommerce.utils.service.melhorEnvio.interfaces;

import com.example.Ecommerce.utils.service.DTOs.CepEntryDTO;
import com.example.Ecommerce.utils.service.DTOs.FreteEntryDTO;

public interface FreteAdpted {

    String calcularFrete(FreteEntryDTO data, CepEntryDTO cep_destino);
}
