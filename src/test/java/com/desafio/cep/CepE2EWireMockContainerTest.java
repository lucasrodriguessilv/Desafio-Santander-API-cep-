package com.desafio.cep;

import com.desafio.cep.model.CepResponse;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.utility.DockerImageName;
import org.testcontainers.containers.BindMode;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class CepE2EWireMockContainerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    // Inicia o container WireMock com os mappings do projeto montados
    private static final GenericContainer<?> wiremock = new GenericContainer<>(DockerImageName.parse("wiremock/wiremock:2.35.0"))
            .withExposedPorts(8080)
            .withFileSystemBind("./src/main/resources/wiremock/mappings", "/home/wiremock/mappings", BindMode.READ_ONLY)
            .withCommand("--verbose");

    static {
        wiremock.start();
    }

    @DynamicPropertySource
    static void registerProperties(DynamicPropertyRegistry registry) {
        String base = "http://" + wiremock.getHost() + ":" + wiremock.getMappedPort(8080) + "/ws";
        registry.add("viacep.url", () -> base);
    }

    @Test
    public void e2e_buscarCep_retornaRespostaEsperada() {
        String url = "http://localhost:" + port + "/cep/01001000";

        CepResponse response = restTemplate.getForObject(url, CepResponse.class);

        assertThat(response).isNotNull();
        assertThat(response.getCep()).isEqualTo("01001-000");
        assertThat(response.getLocalidade()).isEqualTo("São Paulo");
        assertThat(response.getUf()).isEqualTo("SP");
    }
}
