package com.desafio.cep.client;

import com.desafio.cep.model.CepResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ViaCepClient {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${viacep.url:https://viacep.com.br/ws}")
    private String viacepBaseUrl;

    public CepResponse buscarCep(String cep) {
        String url = viacepBaseUrl + "/" + cep + "/json/";
        return restTemplate.getForObject(url, CepResponse.class);
    }
}
