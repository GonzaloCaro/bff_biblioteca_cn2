package com.biblioteca.bff.controller;

import com.biblioteca.bff.service.AzureFunctionOrchestratorService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/rest")
@CrossOrigin(origins = "*")
public class RestMirrorController {

    @Value("${azure.functions.rest.estudiantes.url}")
    private String restEstudiantesUrl;

    @Value("${azure.functions.rest.libros.url}")
    private String restLibrosUrl;

    private final AzureFunctionOrchestratorService orquestador;

    public RestMirrorController(AzureFunctionOrchestratorService orquestador) {
        this.orquestador = orquestador;
    }

    @GetMapping("/estudiantes")
    public ResponseEntity<String> restEstudiantes() {
        return orquestador.callFunction(restEstudiantesUrl, HttpMethod.GET, null);
    }

    @GetMapping("/libros")
    public ResponseEntity<String> restLibros() {
        return orquestador.callFunction(restLibrosUrl, HttpMethod.GET, null);
    }
}
