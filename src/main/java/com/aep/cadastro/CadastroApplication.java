package com.aep.cadastro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class CadastroApplication {

    public static void main(String[] args) {

        SpringApplication.run(CadastroApplication.class, args);

        RestTemplate restTemplate = new RestTemplate();

        String url = "http://localhost:8080/api/users";

        String json = """
                {
                    "nome": "teste 123",
                    "idade": 20,
                    "vaga": "teste",
                    "observacao": "teste oficial"
                }
                """;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> request = new HttpEntity<>(json, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(
                url,
                request,
                String.class
        );

        System.out.println("Status: " + response.getStatusCode());
        System.out.println("Resposta: " + response.getBody());
    }
}