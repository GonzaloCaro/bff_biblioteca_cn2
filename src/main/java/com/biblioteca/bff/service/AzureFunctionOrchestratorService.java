package com.biblioteca.bff.service;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AzureFunctionOrchestratorService {

    private final RestTemplate restTemplate;

    // Inyectamos RestTemplate (asegurate de tener un @Bean de RestTemplate en tu
    // clase principal)
    public AzureFunctionOrchestratorService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ResponseEntity<String> callFunction(String url, HttpMethod method, Object body) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Object> requestEntity = new HttpEntity<>(body, headers);

        try {
            // Hacemos la petición a la Azure Function y devolvemos la respuesta tal cual
            return restTemplate.exchange(url, method, requestEntity, String.class);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\": \"Error al comunicar con Azure Functions: " + e.getMessage() + "\"}");
        }
    }
}