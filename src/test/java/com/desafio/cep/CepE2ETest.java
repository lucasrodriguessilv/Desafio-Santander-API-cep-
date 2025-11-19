package com.desafio.cep;

import com.desafio.cep.model.CepResponse;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.test.context.ActiveProfiles;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.client.MockRestServiceServer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class CepE2ETest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private org.springframework.web.client.RestTemplate clientRestTemplate;

    private MockRestServiceServer mockServer;

    @DynamicPropertySource
    static void registerProperties(DynamicPropertyRegistry registry) {
        // definimos a URL base que ViaCepClient usará (não precisa existir na rede)
        registry.add("viacep.url", () -> "http://viacep.test/ws");
    }

    @BeforeEach
    public void beforeEach() {
        mockServer = MockRestServiceServer.createServer(clientRestTemplate);
    }

    @Test
    public void e2e_buscarCep_retornaRespostaEsperada() {
        String viacepFull = "http://viacep.test/ws/01001000/json/";
        String viacepResponse = "{\"cep\":\"01001-000\",\"logradouro\":\"Praça da Sé\",\"bairro\":\"Sé\",\"localidade\":\"São Paulo\",\"uf\":\"SP\"}";

        mockServer.expect(requestTo(viacepFull))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(viacepResponse, MediaType.APPLICATION_JSON));

        String url = "http://localhost:" + port + "/cep/01001000";

        CepResponse response = restTemplate.getForObject(url, CepResponse.class);

        mockServer.verify();

        assertThat(response).isNotNull();
        assertThat(response.getCep()).isEqualTo("01001-000");
        assertThat(response.getLocalidade()).isEqualTo("São Paulo");
        assertThat(response.getUf()).isEqualTo("SP");
    }
}
