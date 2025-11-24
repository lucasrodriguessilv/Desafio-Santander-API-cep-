package com.desafio.cep.service;

import com.desafio.cep.model.CepResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class CepIntegrationService {
    // URL base da API (ou do WireMock nos testes) "usar rodar esse comando para subir servidor fake
    // $ java -jar wiremock-standalone-3.3.1.jar --port 8089"
    private static final String API_BASE_URL = "http://localhost:8089";
    private final WebClient webClient;

    public CepIntegrationService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl(API_BASE_URL).build();
    }

    public CepResponse fetchCep(String cep) {
        try {
            return webClient.get()
                    // URL de exemplo do ViaCEP mockada
                    .uri("/ws/{cep}/json/", cep.replaceAll("[^0-9]", ""))
                    .retrieve()
                    // OCP - A forma como o dado é tratado ao retornar
                    .bodyToMono(CepResponse.class)
                    .block(); // Bloqueia de forma síncrona para simplificar o Service
        } catch (Exception e) {
            // Tratamento de erro básico
            System.err.println("Erro ao buscar CEP na API externa: " + e.getMessage());
            return new CepResponse(); // Retorna objeto vazio em caso de falha de conexão
        }
    }
}