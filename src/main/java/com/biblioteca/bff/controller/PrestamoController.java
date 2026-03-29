package com.biblioteca.bff.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.biblioteca.bff.service.LambdaOrchestratorService;

@RestController
@RequestMapping("/api/prestamos")
@CrossOrigin(origins = "*")
public class PrestamoController {
    @Value("${aws.lambda.prestamos.url}")
    private String lambdaUrl;

    private final LambdaOrchestratorService orquestador;

    public PrestamoController(LambdaOrchestratorService orquestador) {
        this.orquestador = orquestador;
    }

    @GetMapping
    public ResponseEntity<String> obtenerPrestamos() {
        return orquestador.callLambda(lambdaUrl, HttpMethod.GET, null);
    }

    @PostMapping
    public ResponseEntity<String> crearPrestamo(@RequestBody Object prestamo) {
        return orquestador.callLambda(lambdaUrl, HttpMethod.POST, prestamo);
    }

    @PutMapping
    public ResponseEntity<String> actualizarPrestamo(@RequestBody Object prestamo) {
        return orquestador.callLambda(lambdaUrl, HttpMethod.PUT, prestamo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarPrestamo(@PathVariable String id) {
        String urlConParametro = lambdaUrl + "?id=" + id;
        return orquestador.callLambda(urlConParametro, HttpMethod.DELETE, null);
    }
}
