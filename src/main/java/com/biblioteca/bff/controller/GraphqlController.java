package com.biblioteca.bff.controller;

import com.biblioteca.bff.service.AzureFunctionOrchestratorService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/graphql")
@CrossOrigin(origins = "*")
public class GraphqlController {

    @Value("${azure.functions.graphql.libros.url}")
    private String graphqlLibrosUrl;

    @Value("${azure.functions.graphql.prestamos.url}")
    private String graphqlPrestamosUrl;

    private final AzureFunctionOrchestratorService orquestador;

    public GraphqlController(AzureFunctionOrchestratorService orquestador) {
        this.orquestador = orquestador;
    }

    @PostMapping("/libros")
    public ResponseEntity<String> graphqlLibros(@RequestBody Object graphqlRequest) {
        return orquestador.callFunction(graphqlLibrosUrl, HttpMethod.POST, graphqlRequest);
    }

    @PostMapping("/prestamos")
    public ResponseEntity<String> graphqlPrestamos(@RequestBody Object graphqlRequest) {
        return orquestador.callFunction(graphqlPrestamosUrl, HttpMethod.POST, graphqlRequest);
    }
}
