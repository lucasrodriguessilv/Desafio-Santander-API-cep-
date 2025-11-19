package com.desafio.cep.client;

import com.desafio.cep.model.CepResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ViaCepClient {

    @Autowired
    private RestTemplate restTemplate;

    public CepResponse buscarCep(String cep) {
        String url = "https://viacep.com.br/ws/" + cep + "/json/";
        return restTemplate.getForObject(url, CepResponse.class);
    }
}
